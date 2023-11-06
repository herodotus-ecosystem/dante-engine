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

package cn.herodotus.engine.message.websocket.domain;

import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: WebSocket通道 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 18:41
 */
public enum WebSocketChannel {
    /**
     * 个人通知
     */
    NOTICE("/notice", "个人通知");

    @Schema(title = "消息端点")
    private final String destination;
    @Schema(title = "说明")
    private final String description;

    private static final Map<String, WebSocketChannel> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCT = new ArrayList<>();

    static {
        for (WebSocketChannel webSocketChannel : WebSocketChannel.values()) {
            INDEX_MAP.put(webSocketChannel.name(), webSocketChannel);
            JSON_STRUCT.add(webSocketChannel.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", webSocketChannel.ordinal())
                            .put("key", webSocketChannel.name())
                            .put("text", webSocketChannel.getDescription())
                            .build());
        }
    }

    WebSocketChannel(String destination, String description) {
        this.destination = destination;
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public String getDescription() {
        return description;
    }

    public static WebSocketChannel getWebSocketChannel(String code) {
        return INDEX_MAP.get(code);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return JSON_STRUCT;
    }
}