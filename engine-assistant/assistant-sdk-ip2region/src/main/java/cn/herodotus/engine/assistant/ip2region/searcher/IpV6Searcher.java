/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl-3.0.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.assistant.ip2region.searcher;

import cn.herodotus.stirrup.core.foundation.utils.type.NumberUtils;
import cn.herodotus.engine.assistant.ip2region.domain.IpAddress;
import cn.herodotus.engine.assistant.ip2region.domain.IpLocation;
import cn.herodotus.engine.assistant.ip2region.utils.IpLocationUtils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: IpV6查询 </p>
 * <p>
 * 基于 <a href="https://github.com/ZX-Inc/zxipdb-java">zxipdb-java</a> 改造
 *
 * @author : gengwei.zheng
 * @date : 2023/10/22 22:15
 */
public class IpV6Searcher {

    private final byte[] data;
    private final long total;
    private final long indexStartOffset;
    private final int offLen;
    private final int ipLen;

    public IpV6Searcher(byte[] data) {
        this.data = data;
        this.indexStartOffset = read8(16);
        this.offLen = read1(6);
        this.ipLen = read1(7);
        this.total = read8(8);
    }

    public IpLocation query(String ip) {
        return query(new IpAddress(ip));
    }

    public IpLocation query(IpAddress ip) {
        long ipFind = find(ip, 0, total);
        long ipOffset = indexStartOffset + ipFind * (ipLen + offLen);
        long ipRecordOffset = read8(ipOffset + ipLen, offLen);
        return IpLocationUtils.toIpV6Location(readRecord(ipRecordOffset));
    }

    private long find(IpAddress ip, long l, long r) {
        if (l + 1 >= r) {
            return l;
        }
        long m = (l + r) / 2;
        byte[] mip = readRaw(indexStartOffset + m * (ipLen + offLen), ipLen);
        IpAddress aip = IpAddress.fromBytesV6LE(mip);
        if (ip.compareTo(aip) < 0) {
            return find(ip, l, m);
        } else {
            return find(ip, m, r);
        }
    }

    private String[] readRecord(long offset) {
        String[] recordInfo = new String[2];
        byte[] rec0;
        byte[] rec1;
        int flag = read1(offset);
        if (flag == 1) {
            long locationOffset = read8(offset + 1, offLen);
            return readRecord(locationOffset);
        } else {
            rec0 = readLocation(offset);
            if (flag == 2) {
                rec1 = readLocation(offset + offLen + 1);
            } else {
                rec1 = readLocation(offset + rec0.length + 1);
            }
        }
        recordInfo[0] = new String(rec0, StandardCharsets.UTF_8);
        recordInfo[1] = new String(rec1, StandardCharsets.UTF_8);
        return recordInfo;
    }

    private byte[] readLocation(long offset) {
        if (offset == 0) {
            return new byte[0];
        }
        int flag = read1(offset);
        // 出错
        if (flag == 0) {
            return new byte[0];
        }
        // 仍然为重定向
        if (flag == 2) {
            offset = read8(offset + 1, offLen);
            return readLocation(offset);
        }
        return readStr(offset);
    }

    private int read1(long offset) {
        return data[NumberUtils.longToInt(offset)];
    }


    private byte[] readRaw(byte[] dist, long offset, int size) {
        System.arraycopy(data, NumberUtils.longToInt(offset), dist, 0, size);
        return dist;
    }

    private byte[] readRaw(long offset, int size) {
        byte[] bytes = new byte[size];
        return readRaw(bytes, offset, size);
    }

    private long read8(long offset) {
        return read8(offset, 8);
    }

    private long read8(long offset, int size) {
        byte[] b = new byte[8];
        readRaw(b, offset, size);
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getLong();
    }

    private byte[] readStr(long offset) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(64);
        int ch = read1(offset);
        while (ch != 0) {
            os.write(ch);
            offset++;
            ch = read1(offset);
        }
        return os.toByteArray();
    }
}
