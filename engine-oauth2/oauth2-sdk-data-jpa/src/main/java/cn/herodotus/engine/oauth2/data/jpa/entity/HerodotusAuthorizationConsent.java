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

package cn.herodotus.engine.oauth2.data.jpa.entity;

import cn.herodotus.engine.assistant.definition.domain.base.AbstractEntity;
import cn.herodotus.engine.oauth2.core.constants.OAuth2Constants;
import cn.herodotus.engine.oauth2.data.jpa.generator.HerodotusAuthorizationConsentId;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>Description: OAuth2 认证确认信息实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/22 17:55
 */
@Entity
@Table(name = "oauth2_authorization_consent", indexes = {
        @Index(name = "oauth2_authorization_consent_rcid_idx", columnList = "registered_client_id"),
        @Index(name = "oauth2_authorization_consent_pn_idx", columnList = "principal_name")})
@IdClass(HerodotusAuthorizationConsentId.class)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_AUTHORIZATION_CONSENT)
public class HerodotusAuthorizationConsent extends AbstractEntity {

    @Id
    @Column(name = "registered_client_id", nullable = false, length = 100)
    private String registeredClientId;

    @Id
    @Column(name = "principal_name", nullable = false, length = 200)
    private String principalName;

    @Column(name = "authorities", nullable = false, length = 1000)
    private String authorities;

    public String getRegisteredClientId() {
        return registeredClientId;
    }

    public void setRegisteredClientId(String registeredClientId) {
        this.registeredClientId = registeredClientId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusAuthorizationConsent that = (HerodotusAuthorizationConsent) o;
        return Objects.equal(registeredClientId, that.registeredClientId) && Objects.equal(principalName, that.principalName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registeredClientId, principalName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("registeredClientId", registeredClientId)
                .add("principalName", principalName)
                .add("authorities", authorities)
                .toString();
    }
}
