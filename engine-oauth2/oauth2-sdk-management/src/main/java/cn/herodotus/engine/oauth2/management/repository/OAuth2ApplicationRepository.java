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

package cn.herodotus.engine.oauth2.management.repository;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Application;

/**
 * <p>Description: OAuth2ApplicationRepository </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/1 18:05
 */
public interface OAuth2ApplicationRepository extends BaseRepository<OAuth2Application, String> {

    /**
     * 根据 Client ID 查询 OAuth2Application
     *
     * @param clientId OAuth2Application 中的 clientId
     * @return {@link OAuth2Application}
     */
    OAuth2Application findByClientId(String clientId);
}
