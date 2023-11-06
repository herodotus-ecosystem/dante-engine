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

package cn.herodotus.engine.oauth2.data.jpa.definition.converter;

import cn.herodotus.engine.oauth2.core.definition.domain.RegisteredClientDetails;
import cn.herodotus.engine.oauth2.data.jpa.jackson2.OAuth2JacksonProcessor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * <p>Description: RegisteredClient 转换器</p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/12 23:07
 */
public abstract class AbstractRegisteredClientConverter<S extends RegisteredClientDetails> extends AbstractOAuth2EntityConverter<S, RegisteredClient> implements RegisteredClientConverter<S> {

    public AbstractRegisteredClientConverter(OAuth2JacksonProcessor jacksonProcessor) {
        super(jacksonProcessor);
    }

    @Override
    public RegisteredClient convert(S details) {
        return RegisteredClientConverter.super.convert(details);
    }
}
