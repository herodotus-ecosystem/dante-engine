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

package cn.herodotus.stirrup.oauth2.data.jpa.jackson2;

import cn.herodotus.stirrup.core.foundation.json.jackson2.Jackson2Constants;
import cn.herodotus.engine.oauth2.core.definition.details.FormLoginWebAuthenticationDetails;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusGrantedAuthority;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/**
 * <p>Description: 自定义 User Details Module </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/17 23:39
 */
public class HerodotusJackson2Module extends SimpleModule {

    public HerodotusJackson2Module() {
        super(HerodotusJackson2Module.class.getName(), Jackson2Constants.VERSION);
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(HerodotusUser.class, HerodotusUserMixin.class);
        context.setMixInAnnotations(HerodotusGrantedAuthority.class, HerodotusGrantedAuthorityMixin.class);
        context.setMixInAnnotations(FormLoginWebAuthenticationDetails.class, FormLoginWebAuthenticationDetailsMixin.class);
    }
}
