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

package cn.herodotus.engine.oauth2.management.definition;

import cn.herodotus.engine.oauth2.core.enums.AllJwsAlgorithm;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

/**
 * <p>Description: 自定义 JwsAlgorithm 转 SAS JwsAlgorithm 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/12/12 20:16
 */
public class ToJwsAlgorithmConverter implements Converter<AllJwsAlgorithm, JwsAlgorithm> {
    @Override
    public JwsAlgorithm convert(AllJwsAlgorithm source) {
        if (ObjectUtils.isNotEmpty(source)) {
            // 如果是签名算法
            if (source.getValue() < AllJwsAlgorithm.HS256.getValue()) {
                return SignatureAlgorithm.from(source.name());
            } else {
                return MacAlgorithm.from(source.name());
            }
        }

        return null;
    }
}
