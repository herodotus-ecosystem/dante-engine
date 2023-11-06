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

package cn.herodotus.engine.supplier.upms.logic.service.security;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.data.core.service.BaseService;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysSocialUser;
import cn.herodotus.engine.supplier.upms.logic.repository.security.SysSocialUserRepository;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 社会化登录用户服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/16 16:29
 */
@Service
public class SysSocialUserService extends BaseService<SysSocialUser, String> {

    private final SysSocialUserRepository sysSocialUserRepository;

    public SysSocialUserService(SysSocialUserRepository sysSocialUserRepository) {
        this.sysSocialUserRepository = sysSocialUserRepository;
    }

    @Override
    public BaseRepository<SysSocialUser, String> getRepository() {
        return sysSocialUserRepository;
    }

    public SysSocialUser findByUuidAndSource(String uuid, String source) {
        return sysSocialUserRepository.findSysSocialUserByUuidAndSource(uuid, source);
    }
}
