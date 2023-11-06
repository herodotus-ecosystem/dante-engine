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

package cn.herodotus.engine.message.websocket.annotation;

import cn.herodotus.engine.message.websocket.configuration.MessageWebSocketConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: 手动开启 WebSocket 配置 </p>
 *
 * 模块中的内容相对独立，而且仅有一个 Configuration，同时无需考虑注入顺序的模块，则使用 @Enable 风格配置
 *
 * @author : gengwei.zheng
 * @date : 2023/10/28 11:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Import(MessageWebSocketConfiguration.class)
public @interface EnableHerodotusWebSocket {
}
