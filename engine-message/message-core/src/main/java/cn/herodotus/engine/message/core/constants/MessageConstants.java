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

package cn.herodotus.engine.message.core.constants;

import cn.herodotus.engine.assistant.core.constants.BaseConstants;

/**
 * <p>Description: 消息模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/4 17:06
 */
public interface MessageConstants extends BaseConstants {

    String MESSAGE_AREA_PREFIX = AREA_PREFIX + "message:";

    String PROPERTY_PREFIX_KAFKA = PROPERTY_PREFIX_EVENT + ".kafka";
    String PROPERTY_PREFIX_WEBSOCKET = PROPERTY_PREFIX_MESSAGE + ".websocket";
    String PROPERTY_PREFIX_MQTT = PROPERTY_PREFIX_MESSAGE + ".mqtt";

    String MULTIPLE_INSTANCE_INPUT = "webSocketMultipleInstanceSyncInput";
    String MULTIPLE_INSTANCE_OUTPUT = "webSocketMultipleInstanceSyncOutput";

    /**
     * 消息类型判断值。
     */
    String MESSAGE_TO_ALL = "message_to_all";

    String ITEM_KAFKA_ENABLED = PROPERTY_PREFIX_KAFKA + PROPERTY_ENABLED;
    String ITEM_WEBSOCKET_MULTIPLE_INSTANCE = PROPERTY_PREFIX_WEBSOCKET + ".mode";
    String ITEM_MQTT_USERNAME = PROPERTY_PREFIX_MQTT + ".username";
    String ITEM_MQTT_PASSWORD = PROPERTY_PREFIX_MQTT + ".password";

    String REDIS_CURRENT_ONLINE_USER = MESSAGE_AREA_PREFIX + "online:user";
    String REGION_MESSAGE_ANNOUNCEMENT = MESSAGE_AREA_PREFIX + "system_announcement";
    String REGION_MESSAGE_DIALOGUE_CONTACT = MESSAGE_AREA_PREFIX + "personal:contact";
    String REGION_MESSAGE_DIALOGUE = MESSAGE_AREA_PREFIX + "personal:dialogue";
    String REGION_MESSAGE_DIALOGUE_DETAIL = MESSAGE_AREA_PREFIX + "personal:dialogue:detail";
    String REGION_MESSAGE_NOTIFICATION = MESSAGE_AREA_PREFIX + "notification_queue";
    String REGION_MESSAGE_PULL_STAMP = MESSAGE_AREA_PREFIX + "pull_stamp";
    String WEBSOCKET_CHANNEL_PROXY_BROADCAST = "/broadcast";
    String WEBSOCKET_CHANNEL_PROXY_PERSONAL = "/personal";
    String WEBSOCKET_DESTINATION_BROADCAST_NOTICE = WEBSOCKET_CHANNEL_PROXY_BROADCAST + "/notice";
    String WEBSOCKET_DESTINATION_BROADCAST_ONLINE = WEBSOCKET_CHANNEL_PROXY_BROADCAST + "/online";
    String WEBSOCKET_DESTINATION_PERSONAL_MESSAGE = WEBSOCKET_CHANNEL_PROXY_PERSONAL + "/message";
}
