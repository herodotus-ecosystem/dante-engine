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

package cn.herodotus.engine.oauth2.data.jpa.jackson2;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.JsonNodeUtils;
import cn.herodotus.engine.oauth2.core.definition.details.FormLoginWebAuthenticationDetails;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * <p>Description: FormLoginWebAuthenticationDetailsDeserializer </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/4/14 11:48
 */
public class FormLoginWebAuthenticationDetailsDeserializer extends JsonDeserializer<FormLoginWebAuthenticationDetails> {
    @Override
    public FormLoginWebAuthenticationDetails deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        String remoteAddress = JsonNodeUtils.findStringValue(jsonNode, "remoteAddress");
        String sessionId = JsonNodeUtils.findStringValue(jsonNode, "sessionId");
        String parameterName = JsonNodeUtils.findStringValue(jsonNode, "parameterName");
        String category = JsonNodeUtils.findStringValue(jsonNode, "category");
        String code = JsonNodeUtils.findStringValue(jsonNode, "code");
        String identity = JsonNodeUtils.findStringValue(jsonNode, "identity");
        boolean closed = JsonNodeUtils.findBooleanValue(jsonNode, "closed");

        return new FormLoginWebAuthenticationDetails(remoteAddress, sessionId, closed, parameterName, category, code, identity);
    }
}
