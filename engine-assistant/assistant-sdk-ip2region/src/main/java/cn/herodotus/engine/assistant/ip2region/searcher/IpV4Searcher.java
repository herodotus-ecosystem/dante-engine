/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.assistant.ip2region.searcher;

import cn.herodotus.engine.assistant.ip2region.domain.Header;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class IpV4Searcher {

    // constant defined copied from the xdb maker
    public static final int HEADER_INFO_LENGTH = 256;
    public static final int VECTOR_INDEX_ROWS = 256;
    public static final int VECTOR_INDEX_COLS = 256;
    public static final int VECTOR_INDEX_SIZE = 8;
    public static final int SEGMENT_INDEX_SIZE = 14;

    // random access file handle for file based search
    private final RandomAccessFile handle;

    private int ioCount = 0;

    // vector index.
    // use the byte[] instead of VectorIndex entry array to keep
    // the minimal memory allocation.
    private final byte[] vectorIndex;

    // xdb content buffer, used for in-memory search
    private final byte[] contentBuff;

    // --- static method to create searchers

    public static IpV4Searcher newWithFileOnly(String dbPath) throws IOException {
        return new IpV4Searcher(dbPath, null, null);
    }

    public static IpV4Searcher newWithVectorIndex(String dbPath, byte[] vectorIndex) throws IOException {
        return new IpV4Searcher(dbPath, vectorIndex, null);
    }

    public static IpV4Searcher newWithBuffer(byte[] cBuff) throws IOException {
        return new IpV4Searcher(null, null, cBuff);
    }

    // --- End of creator

    public IpV4Searcher(String dbFile, byte[] vectorIndex, byte[] cBuff) throws IOException {
        if (cBuff != null) {
            this.handle = null;
            this.vectorIndex = null;
            this.contentBuff = cBuff;
        } else {
            this.handle = new RandomAccessFile(dbFile, "r");
            this.vectorIndex = vectorIndex;
            this.contentBuff = null;
        }
    }

    public void close() throws IOException {
        if (this.handle != null) {
            this.handle.close();
        }
    }

    public int getIOCount() {
        return ioCount;
    }

    public String search(String[] ipParts) throws IOException {
        return search(getIpAdder(ipParts));
    }

    public String search(long ip) throws IOException {
        // reset the global counter
        this.ioCount = 0;
        // locate the segment index block based on the vector index
        int sPtr;
        int ePtr;
        int il0 = (int) ((ip >> 24) & 0xFF);
        int il1 = (int) ((ip >> 16) & 0xFF);
        int idx = il0 * VECTOR_INDEX_COLS * VECTOR_INDEX_SIZE + il1 * VECTOR_INDEX_SIZE;
        if (vectorIndex != null) {
            sPtr = getInt(vectorIndex, idx);
            ePtr = getInt(vectorIndex, idx + 4);
        } else if (contentBuff != null) {
            sPtr = getInt(contentBuff, HEADER_INFO_LENGTH + idx);
            ePtr = getInt(contentBuff, HEADER_INFO_LENGTH + idx + 4);
        } else {
            final byte[] buff = new byte[8];
            read(HEADER_INFO_LENGTH + idx, buff);
            sPtr = getInt(buff, 0);
            ePtr = getInt(buff, 4);
        }
        // binary search the segment index block to get the region info
        final byte[] buff = new byte[SEGMENT_INDEX_SIZE];
        int dataLen = -1;
        int dataPtr = -1;
        int l = 0;
        int h = (ePtr - sPtr) / SEGMENT_INDEX_SIZE;
        while (l <= h) {
            int m = (l + h) >> 1;
            int p = sPtr + m * SEGMENT_INDEX_SIZE;
            // read the segment index
            read(p, buff);
            long sip = getIntLong(buff, 0);
            if (ip < sip) {
                h = m - 1;
            } else {
                long eip = getIntLong(buff, 4);
                if (ip > eip) {
                    l = m + 1;
                } else {
                    dataLen = getInt2(buff, 8);
                    dataPtr = getInt(buff, 10);
                    break;
                }
            }
        }
        // empty match interception
        if (dataPtr < 0) {
            return null;
        }
        // load and return the region data
        final byte[] regionBuff = new byte[dataLen];
        read(dataPtr, regionBuff);
        return new String(regionBuff, StandardCharsets.UTF_8);
    }

    private void read(int offset, byte[] buffer) throws IOException {
        // check the in-memory buffer first
        if (contentBuff != null) {
            // @TODO: reduce data copying, directly decode the data ?
            System.arraycopy(contentBuff, offset, buffer, 0, buffer.length);
            return;
        }
        // read from the file handle
        assert handle != null;
        handle.seek(offset);
        this.ioCount++;
        int rLen = handle.read(buffer);
        if (rLen != buffer.length) {
            throw new IOException("incomplete read: read bytes should be " + buffer.length);
        }
    }

    // --- static cache util function

    public static Header loadHeader(RandomAccessFile handle) throws IOException {
        handle.seek(0);
        final byte[] buff = new byte[HEADER_INFO_LENGTH];
        handle.read(buff);
        return new Header(buff);
    }

    public static Header loadHeaderFromFile(String dbPath) throws IOException {
        RandomAccessFile handle = new RandomAccessFile(dbPath, "r");
        return loadHeader(handle);
    }

    public static byte[] loadVectorIndex(RandomAccessFile handle) throws IOException {
        handle.seek(HEADER_INFO_LENGTH);
        int len = VECTOR_INDEX_ROWS * VECTOR_INDEX_COLS * SEGMENT_INDEX_SIZE;
        final byte[] buff = new byte[len];
        int rLen = handle.read(buff);
        if (rLen != len) {
            throw new IOException("incomplete read: read bytes should be " + len);
        }
        return buff;
    }

    public static byte[] loadVectorIndexFromFile(String dbPath) throws IOException {
        RandomAccessFile handle = new RandomAccessFile(dbPath, "r");
        return loadVectorIndex(handle);
    }

    public static byte[] loadContent(RandomAccessFile handle) throws IOException {
        handle.seek(0);
        final byte[] buff = new byte[(int) handle.length()];
        int rLen = handle.read(buff);
        if (rLen != buff.length) {
            throw new IOException("incomplete read: read bytes should be " + buff.length);
        }
        return buff;
    }

    public static byte[] loadContentFromFile(String dbPath) throws IOException {
        RandomAccessFile handle = new RandomAccessFile(dbPath, "r");
        return loadContent(handle);
    }

    // --- End cache load util function

    // --- static util method

    /**
     * get an int from a byte array start from the specified offset
     */
    public static long getIntLong(byte[] b, int offset) {
        return (b[offset++] & 0x000000FFL) |
                ((b[offset++] << 8) & 0x0000FF00L) |
                ((b[offset++] << 16) & 0x00FF0000L) |
                ((b[offset] << 24) & 0xFF000000L);
    }

    public static int getInt(byte[] b, int offset) {
        return (b[offset++] & 0x000000FF) |
                ((b[offset++] << 8) & 0x0000FF00) |
                ((b[offset++] << 16) & 0x00FF0000) |
                ((b[offset] << 24) & 0xFF000000);
    }

    public static int getInt2(byte[] b, int offset) {
        return (b[offset++] & 0x000000FF) |
                (b[offset] & 0x0000FF00);
    }

    /**
     * long int to ip string
     *
     * @param ip long ip
     * @return ip 字符串
     */
    public static String long2ip(long ip) {
        return String.valueOf((ip >> 24) & 0xFF) + '.' +
                ((ip >> 16) & 0xFF) + '.' + ((ip >> 8) & 0xFF) + '.' + ((ip) & 0xFF);
    }

    private static final byte[] SHIFT_INDEX = {24, 16, 8, 0};

    /**
     * check the specified ip address
     *
     * @param ipParts ip part 数组
     * @return ip long
     */
    public static long getIpAdder(String[] ipParts) {
        long ipAdder = 0;
        for (int i = 0; i < ipParts.length; i++) {
            int val = Integer.parseInt(ipParts[i]);
            if (val > 255) {
                throw new IllegalArgumentException("ip part `" + ipParts[i] + "` should be less then 256");
            }
            ipAdder |= ((long) val << SHIFT_INDEX[i]);
        }
        return ipAdder & 0xFFFFFFFFL;
    }
}
