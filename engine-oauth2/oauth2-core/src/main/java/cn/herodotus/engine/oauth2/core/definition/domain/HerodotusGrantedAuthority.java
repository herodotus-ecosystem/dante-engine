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

package cn.herodotus.engine.oauth2.core.definition.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>Description: 自定义 GrantedAuthority </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/5 0:12
 */
public class HerodotusGrantedAuthority implements GrantedAuthority {

    private String authority;

    public HerodotusGrantedAuthority() {
    }

    public HerodotusGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusGrantedAuthority that = (HerodotusGrantedAuthority) o;
        return Objects.equal(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authority);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("authority", authority)
                .toString();
    }
}