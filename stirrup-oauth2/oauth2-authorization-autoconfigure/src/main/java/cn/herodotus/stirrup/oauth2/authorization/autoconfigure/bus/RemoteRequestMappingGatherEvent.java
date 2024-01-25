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

package cn.herodotus.stirrup.oauth2.authorization.autoconfigure.bus;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * <p>Description: Request Mapping 收集远程事件 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/6 11:23
 */
public class RemoteRequestMappingGatherEvent extends RemoteApplicationEvent {

    private String data;

    public RemoteRequestMappingGatherEvent() {
        super();
    }

    public RemoteRequestMappingGatherEvent(String data, String originService, String destinationService) {
        super(data, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
