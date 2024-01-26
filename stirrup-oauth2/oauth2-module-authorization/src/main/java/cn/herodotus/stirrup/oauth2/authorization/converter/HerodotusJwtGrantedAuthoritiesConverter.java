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

package cn.herodotus.stirrup.oauth2.authorization.converter;

import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusGrantedAuthority;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * <p>Description: Jwt 权限数据转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/13 14:21
 */
public class HerodotusJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String DEFAULT_AUTHORITY_PREFIX = "SCOPE_";
    private static final Collection<String> WELL_KNOWN_AUTHORITIES_CLAIM_NAMES = Arrays.asList("scope", "scp");
    private final Log logger = LogFactory.getLog(getClass());
    private String authorityPrefix = DEFAULT_AUTHORITY_PREFIX;

    private String authoritiesClaimName;

    /**
     * Extract {@link GrantedAuthority}s from the given {@link Jwt}.
     *
     * @param jwt The {@link Jwt} token
     * @return The {@link GrantedAuthority authorities} read from the token scopes
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : getAuthorities(jwt)) {
            grantedAuthorities.add(new HerodotusGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }

    /**
     * Sets the prefix to use for {@link GrantedAuthority authorities} mapped by this
     * converter. Defaults to
     *
     * @param authorityPrefix The authority prefix
     * @since 5.2
     */
    public void setAuthorityPrefix(String authorityPrefix) {
        Assert.notNull(authorityPrefix, "authorityPrefix cannot be null");
        this.authorityPrefix = authorityPrefix;
    }

    /**
     * Sets the name of token claim to use for mapping {@link GrantedAuthority
     * authorities} by this converter. Defaults to
     *
     * @param authoritiesClaimName The token claim name to map authorities
     * @since 5.2
     */
    public void setAuthoritiesClaimName(String authoritiesClaimName) {
        Assert.hasText(authoritiesClaimName, "authoritiesClaimName cannot be empty");
        this.authoritiesClaimName = authoritiesClaimName;
    }

    private String getAuthoritiesClaimName(Jwt jwt) {
        if (this.authoritiesClaimName != null) {
            return this.authoritiesClaimName;
        }
        for (String claimName : WELL_KNOWN_AUTHORITIES_CLAIM_NAMES) {
            if (jwt.hasClaim(claimName)) {
                return claimName;
            }
        }
        return null;
    }

    private Collection<String> getAuthorities(Jwt jwt) {
        String claimName = getAuthoritiesClaimName(jwt);
        if (claimName == null) {
            this.logger.trace("Returning no authorities since could not find any claims that might contain scopes");
            return Collections.emptyList();
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.format("Looking for scopes in claim %s", claimName));
        }
        Object authorities = jwt.getClaim(claimName);
        if (authorities instanceof String) {
            if (StringUtils.hasText((String) authorities)) {
                return Arrays.asList(((String) authorities).split(" "));
            }
            return Collections.emptyList();
        }
        if (authorities instanceof Collection) {
            return castAuthoritiesToCollection(authorities);
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private Collection<String> castAuthoritiesToCollection(Object authorities) {
        return (Collection<String>) authorities;
    }
}
