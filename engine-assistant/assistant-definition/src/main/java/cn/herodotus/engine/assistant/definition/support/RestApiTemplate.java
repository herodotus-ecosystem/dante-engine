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

package cn.herodotus.engine.assistant.definition.support;

import cn.zhxu.okhttps.HTTP;
import cn.zhxu.okhttps.MsgConvertor;
import cn.zhxu.okhttps.jackson.JacksonMsgConvertor;

/**
 * <p>Description: 外部 Rest API 集成抽象服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/10 15:33
 */
public interface RestApiTemplate {

    /**
     * 获取外部Rest API基础地址
     *
     * @return 访问接口的统一BaseURL
     */
    String getBaseUrl();

    default HTTP http() {
        return HTTP.builder()
                .baseUrl(getBaseUrl())
                .addMsgConvertor(getMsgConvertor())
                .build();
    }

    default MsgConvertor getMsgConvertor() {
        return new JacksonMsgConvertor();
    }
}
