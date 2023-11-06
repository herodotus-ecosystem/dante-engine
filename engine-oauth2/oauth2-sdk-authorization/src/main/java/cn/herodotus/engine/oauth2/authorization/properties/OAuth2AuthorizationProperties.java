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

package cn.herodotus.engine.oauth2.authorization.properties;

import cn.herodotus.engine.assistant.core.enums.Target;
import cn.herodotus.engine.oauth2.core.constants.OAuth2Constants;
import cn.herodotus.engine.oauth2.core.enums.Certificate;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p>Description: OAuth2 配置属性 </p>
 * <p>
 * 仅认证服务会使用到的安全相关配置，这是与 OAuth2Properties 的主要区别。
 *
 * @author : gengwei.zheng
 * @date : 2022/3/6 16:36
 */
@ConfigurationProperties(prefix = OAuth2Constants.PROPERTY_OAUTH2_AUTHORIZATION)
public class OAuth2AuthorizationProperties {

    /**
     * Token 校验是采用远程方式还是本地方式。
     */
    private Target validate = Target.REMOTE;

    /**
     * 是否使用严格模式。严格模式一定要求有权限，非严格模式没有权限管控的接口，只要认证通过也可以使用。
     */
    private Boolean strict = true;

    /**
     * JWT的密钥或者密钥对(JSON Web Key) 配置
     */
    private Jwk jwk = new Jwk();
    /**
     * 指定 Request Matcher 静态安全规则
     */
    private Matcher matcher = new Matcher();

    public Target getValidate() {
        return validate;
    }

    public void setValidate(Target validate) {
        this.validate = validate;
    }

    public Boolean getStrict() {
        return strict;
    }

    public void setStrict(Boolean strict) {
        this.strict = strict;
    }

    public Jwk getJwk() {
        return jwk;
    }

    public void setJwk(Jwk jwk) {
        this.jwk = jwk;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public static class Jwk {

        /**
         * 证书策略：standard OAuth2 标准证书模式；custom 自定义证书模式
         */
        private Certificate certificate = Certificate.CUSTOM;
        /**
         * jks证书文件路径
         */
        private String jksKeyStore = "classpath*:certificate/herodotus-cloud.jks";
        /**
         * jks证书密码
         */
        private String jksKeyPassword = "Herodotus-Cloud";
        /**
         * jks证书密钥库密码
         */
        private String jksStorePassword = "Herodotus-Cloud";
        /**
         * jks证书别名
         */
        private String jksKeyAlias = "herodotus-cloud";

        public Certificate getCertificate() {
            return certificate;
        }

        public void setCertificate(Certificate certificate) {
            this.certificate = certificate;
        }

        public String getJksKeyStore() {
            return jksKeyStore;
        }

        public void setJksKeyStore(String jksKeyStore) {
            this.jksKeyStore = jksKeyStore;
        }

        public String getJksKeyPassword() {
            return jksKeyPassword;
        }

        public void setJksKeyPassword(String jksKeyPassword) {
            this.jksKeyPassword = jksKeyPassword;
        }

        public String getJksStorePassword() {
            return jksStorePassword;
        }

        public void setJksStorePassword(String jksStorePassword) {
            this.jksStorePassword = jksStorePassword;
        }

        public String getJksKeyAlias() {
            return jksKeyAlias;
        }

        public void setJksKeyAlias(String jksKeyAlias) {
            this.jksKeyAlias = jksKeyAlias;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("certificate", certificate)
                    .add("jksKeyStore", jksKeyStore)
                    .add("jksKeyPassword", jksKeyPassword)
                    .add("jksStorePassword", jksStorePassword)
                    .add("jksKeyAlias", jksKeyAlias)
                    .toString();
        }

        private enum Strategy {
            STANDARD, CUSTOM
        }
    }

    /**
     * 用于手动的指定 Request Matcher 安全规则。
     * <p>
     * permitAll 比较常用，因此先只增加该项。后续可根据需要添加
     */
    public static class Matcher {
        /**
         * 静态资源过滤
         */
        private List<String> staticResources;
        /**
         * Security "permitAll" 权限列表。
         */
        private List<String> permitAll;
        /**
         * 只校验是否请求中包含Token，不校验Token中是否包含该权限的资源
         */
        private List<String> hasAuthenticated;

        public List<String> getStaticResources() {
            return staticResources;
        }

        public void setStaticResources(List<String> staticResources) {
            this.staticResources = staticResources;
        }

        public List<String> getPermitAll() {
            return permitAll;
        }

        public void setPermitAll(List<String> permitAll) {
            this.permitAll = permitAll;
        }

        public List<String> getHasAuthenticated() {
            return hasAuthenticated;
        }

        public void setHasAuthenticated(List<String> hasAuthenticated) {
            this.hasAuthenticated = hasAuthenticated;
        }
    }
}
