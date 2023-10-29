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

package cn.herodotus.engine.oauth2.management.compliance.listener;

import cn.herodotus.engine.message.core.logic.event.AccountReleaseFromCacheEvent;
import cn.herodotus.engine.oauth2.management.compliance.OAuth2AccountStatusManager;
import org.springframework.context.ApplicationListener;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/14 14:33
 */
public class AccountReleaseFromCacheListener implements ApplicationListener<AccountReleaseFromCacheEvent> {

    private final OAuth2AccountStatusManager accountStatusManager;

    public AccountReleaseFromCacheListener(OAuth2AccountStatusManager accountStatusManager) {
        this.accountStatusManager = accountStatusManager;
    }

    @Override
    public void onApplicationEvent(AccountReleaseFromCacheEvent event) {
        String username = event.getData();
        accountStatusManager.releaseFromCache(username);
    }
}
