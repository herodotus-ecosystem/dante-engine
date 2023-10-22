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

package cn.herodotus.engine.rest.core.definition;

import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

/**
 * <p>Description: OpenApi Server 处理器 </p>
 * <p>
 * 单体和分布式架构，提供给OpenAPI展示的地址不同。
 *
 * @author : gengwei.zheng
 * @date : 2022/1/16 18:56
 */
public interface OpenApiServerResolver {

    /**
     * 获取 Open Api 所需的 Server 地址。
     *
     * @return Open Api Servers 值
     */
    List<Server> getServers();
}
