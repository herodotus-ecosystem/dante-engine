/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.message.core.constants;

import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;

/**
 * <p>Description: 消息模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/4 17:06
 */
public interface MessageConstants extends BaseConstants {

    String REGION_INFORMATION_SYSTEM_ANNOUNCEMENT = AREA_PREFIX + "information:system_announcement";
    String REGION_INFORMATION_PERSONAL_CONTACT = AREA_PREFIX + "information:personal_contact";
    String REGION_INFORMATION_PERSONAL_DIALOGUE = AREA_PREFIX + "information:personal_dialogue";
    String REGION_INFORMATION_PERSONAL_DIALOGUE_DETAIL = AREA_PREFIX + "information:personal_dialogue:detail";
    String REGION_INFORMATION_NOTIFICATION_QUEUE = AREA_PREFIX + "information:notification_queue";
    String REGION_INFORMATION_MESSAGE_PULL_STAMP = AREA_PREFIX + "information:pull_stamp";

    String PROPERTY_PREFIX_MESSAGE = PROPERTY_PREFIX_HERODOTUS + ".message";

    String PROPERTY_PREFIX_SOCKET_IO = PROPERTY_PREFIX_MESSAGE + ".socketio";
    String PROPERTY_PREFIX_WEBSOCKET = PROPERTY_PREFIX_MESSAGE + ".websocket";

    String WEBSOCKET_CHANNEL_PROXY_BROADCAST = "/broadcast";
    String WEBSOCKET_CHANNEL_PROXY_PEER_TO_PEER = "/personal";
    String WEBSOCKET_DESTINATION_BROADCAST_NOTICE = WEBSOCKET_CHANNEL_PROXY_BROADCAST + "/notice";
    String WEBSOCKET_DESTINATION_PERSONAL_MESSAGE = WEBSOCKET_CHANNEL_PROXY_PEER_TO_PEER + "/message";
}
