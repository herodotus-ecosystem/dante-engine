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

package cn.herodotus.stirrup.oauth2.data.jpa.entity;

import cn.herodotus.engine.oauth2.core.constants.OAuth2Constants;
import cn.herodotus.stirrup.oauth2.data.jpa.definition.domain.AbstractRegisteredClient;
import cn.herodotus.stirrup.oauth2.data.jpa.generator.HerodotusRegisteredClientUuidGenerator;
import com.google.common.base.MoreObjects;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

/**
 * <p>Description: OAuth2 客户端实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/22 17:18
 */
@Entity
@Table(name = "oauth2_registered_client", indexes = {
        @Index(name = "oauth2_registered_client_id_idx", columnList = "id"),
        @Index(name = "oauth2_registered_client_cid_idx", columnList = "client_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_REGISTERED_CLIENT)
public class HerodotusRegisteredClient extends AbstractRegisteredClient {

    @Id
    @HerodotusRegisteredClientUuidGenerator
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @Column(name = "client_id", nullable = false, length = 100)
    private String clientId;

    @Column(name = "client_secret", length = 200)
    private String clientSecret;

    @Column(name = "client_name", nullable = false, length = 200)
    private String clientName;

    @Column(name = "scopes", nullable = false, length = 1000)
    private String scopes;

    @Column(name = "client_settings", nullable = false, length = 2000)
    private String clientSettings;

    @Column(name = "token_settings", nullable = false, length = 2000)
    private String tokenSettings;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getClientSettings() {
        return clientSettings;
    }

    public void setClientSettings(String clientSettings) {
        this.clientSettings = clientSettings;
    }

    public String getTokenSettings() {
        return tokenSettings;
    }

    public void setTokenSettings(String tokenSettings) {
        this.tokenSettings = tokenSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusRegisteredClient that = (HerodotusRegisteredClient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("clientId", clientId)
                .add("clientSecret", clientSecret)
                .add("clientName", clientName)
                .add("scopes", scopes)
                .add("clientSettings", clientSettings)
                .add("tokenSettings", tokenSettings)
                .toString();
    }
}
