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

package cn.herodotus.engine.oauth2.management.compliance;

import cn.herodotus.engine.data.core.enums.DataItemStatus;
import cn.herodotus.engine.message.core.logic.strategy.AccountStatusEventManager;
import cn.herodotus.engine.message.core.logic.domain.UserStatus;
import cn.herodotus.engine.oauth2.authentication.stamp.LockedUserDetailsStampManager;
import cn.herodotus.engine.oauth2.core.definition.domain.HerodotusUser;
import cn.herodotus.engine.oauth2.core.definition.service.EnhanceUserDetailsService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 账户锁定处理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/8 19:25
 */
@Service
public class OAuth2AccountStatusManager {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AccountStatusManager.class);

    private final UserDetailsService userDetailsService;
    private final AccountStatusEventManager accountStatusEventManager;
    private final LockedUserDetailsStampManager lockedUserDetailsStampManager;

    public OAuth2AccountStatusManager(UserDetailsService userDetailsService, AccountStatusEventManager accountStatusEventManager, LockedUserDetailsStampManager lockedUserDetailsStampManager) {
        this.userDetailsService = userDetailsService;
        this.lockedUserDetailsStampManager = lockedUserDetailsStampManager;
        this.accountStatusEventManager = accountStatusEventManager;
    }

    private EnhanceUserDetailsService getUserDetailsService() {
        return (EnhanceUserDetailsService) userDetailsService;
    }

    private String getUserId(String username) {
        EnhanceUserDetailsService enhanceUserDetailsService = getUserDetailsService();
        HerodotusUser user = enhanceUserDetailsService.loadHerodotusUserByUsername(username);
        if (ObjectUtils.isNotEmpty(user)) {
            return user.getUserId();
        }

        log.warn("[Herodotus] |- Can not found the userid for [{}]", username);
        return null;
    }

    public void lock(String username) {
        String userId = getUserId(username);
        if (ObjectUtils.isNotEmpty(userId)) {
            accountStatusEventManager.postProcess(new UserStatus(userId, DataItemStatus.LOCKING.name()));
            lockedUserDetailsStampManager.put(userId, username);
            log.info("[Herodotus] |- User count [{}] has been locked, and record into cache!", username);
        }
    }

    public void enable(String userId) {
        if (ObjectUtils.isNotEmpty(userId)) {
            accountStatusEventManager.postProcess(new UserStatus(userId, DataItemStatus.ENABLE.name()));
        }
    }

    public void releaseFromCache(String username) {
        String userId = getUserId(username);
        if (ObjectUtils.isNotEmpty(userId)) {
            String value = lockedUserDetailsStampManager.get(userId);
            if (StringUtils.isNotEmpty(value)) {
                this.lockedUserDetailsStampManager.delete(userId);
                log.info("[Herodotus] |- User count [{}] locked info has been release!", username);
            }
        }
    }
}
