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

import cn.herodotus.engine.oauth2.data.jpa.jackson2.OAuth2JacksonProcessor;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * <p>Description: 封装RegisteredClientAdapter 默认行为 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/12 23:54
 */
public abstract class AbstractOAuth2EntityConverter<S, T> implements Converter<S, T> {

    private final OAuth2JacksonProcessor jacksonProcessor;

    public AbstractOAuth2EntityConverter(OAuth2JacksonProcessor jacksonProcessor) {
        this.jacksonProcessor = jacksonProcessor;
    }

    protected Map<String, Object> parseMap(String data) {
        return jacksonProcessor.parseMap(data);
    }

    protected String writeMap(Map<String, Object> data) {
        return jacksonProcessor.writeMap(data);
    }
}
