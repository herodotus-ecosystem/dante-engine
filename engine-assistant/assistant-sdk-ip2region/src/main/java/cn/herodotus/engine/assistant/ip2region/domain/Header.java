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

package cn.herodotus.engine.assistant.ip2region.domain;

import cn.herodotus.engine.assistant.ip2region.searcher.IpV4Searcher;
import com.google.common.base.MoreObjects;

public class Header {

    public final int version;
    public final int indexPolicy;
    public final int createdAt;
    public final int startIndexPtr;
    public final int endIndexPtr;
    public final byte[] buffer;

    public Header(byte[] buff) {
        assert buff.length >= 16;
        version = IpV4Searcher.getInt2(buff, 0);
        indexPolicy = IpV4Searcher.getInt2(buff, 2);
        createdAt = IpV4Searcher.getInt(buff, 4);
        startIndexPtr = IpV4Searcher.getInt(buff, 8);
        endIndexPtr = IpV4Searcher.getInt(buff, 12);
        buffer = buff;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("version", version)
                .add("indexPolicy", indexPolicy)
                .add("createdAt", createdAt)
                .add("startIndexPtr", startIndexPtr)
                .add("endIndexPtr", endIndexPtr)
                .add("buffer", buffer)
                .toString();
    }
}
