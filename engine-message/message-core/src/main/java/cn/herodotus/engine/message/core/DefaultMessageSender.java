/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.message.core;

import cn.herodotus.engine.message.core.constants.MessageConstants;

/**
 * <p>Description: 默认消息发送器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/26 21:29
 */
public class DefaultMessageSender extends MessageSender{

    /**
     * 发送 WebSocket 给指定用户
     * @param user 用户唯一标识
     * @param payload 消息内容
     * @param <T> 消息内容类型
     */
    public static <T> void toUser(String user, T payload) {
        pointToPoint(user, MessageConstants.WEBSOCKET_DESTINATION_PERSONAL_MESSAGE, payload);
    }
}
