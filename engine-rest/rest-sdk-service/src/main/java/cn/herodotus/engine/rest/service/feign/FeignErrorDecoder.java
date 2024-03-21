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

package cn.herodotus.engine.rest.service.feign;

import cn.herodotus.engine.assistant.definition.domain.Result;
import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import cn.herodotus.engine.rest.core.exception.FeignDecodeIOException;
import com.fasterxml.jackson.databind.JavaType;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: Feign 错误信息解码器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/30 10:58
 */
public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            String content = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            Result<String> result = Result.failure("Feign 远程调用" + methodKey + " 出错");
            JavaType javaType = Jackson2Utils.getTypeFactory().constructParametricType(Result.class, String.class);
            Result<String> object = Jackson2Utils.toObject(content, javaType);
            if (ObjectUtils.isNotEmpty(object)) {
                result = object;
            }
            return new FeignRemoteCallExceptionWrapper(result);
        } catch (IOException e) {
            log.error("[Herodotus] |- Feign invoke [{}] error decoder convert result catch io exception.", methodKey, e);
            return new FeignDecodeIOException();
        }
    }
}
