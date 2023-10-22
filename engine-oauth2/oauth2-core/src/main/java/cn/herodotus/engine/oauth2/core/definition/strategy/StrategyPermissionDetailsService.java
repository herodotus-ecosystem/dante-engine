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

package cn.herodotus.engine.oauth2.core.definition.strategy;

import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusPermission;

import java.util.List;

/**
 * <p>Description: 系统范围服务策略定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/31 22:34
 */
public interface StrategyPermissionDetailsService {

    /**
     * 获取全部权限
     *
     * @return 权限集合
     */
    List<HerodotusPermission> findAll();
}
