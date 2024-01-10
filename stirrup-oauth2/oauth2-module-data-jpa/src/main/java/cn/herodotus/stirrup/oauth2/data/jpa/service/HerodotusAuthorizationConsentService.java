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

package cn.herodotus.stirrup.oauth2.data.jpa.service;

import cn.herodotus.stirrup.data.kernel.repository.BaseRepository;
import cn.herodotus.stirrup.data.kernel.service.BaseService;
import cn.herodotus.stirrup.oauth2.data.jpa.entity.HerodotusAuthorizationConsent;
import cn.herodotus.stirrup.oauth2.data.jpa.generator.HerodotusAuthorizationConsentId;
import cn.herodotus.stirrup.oauth2.data.jpa.repository.HerodotusAuthorizationConsentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>Description: HerodotusAuthorizationConsentService </p>
 * <p>
 * 这里命名没有按照统一的习惯，主要是为了防止与 spring-authorization-server 已有类的同名而导致Bean注入失败
 *
 * @author : gengwei.zheng
 * @date : 2022/2/25 21:02
 */
@Service
public class HerodotusAuthorizationConsentService extends BaseService<HerodotusAuthorizationConsent, HerodotusAuthorizationConsentId> {

    private static final Logger log = LoggerFactory.getLogger(HerodotusAuthorizationConsentService.class);

    private final HerodotusAuthorizationConsentRepository authorizationConsentRepository;

    @Autowired
    public HerodotusAuthorizationConsentService(HerodotusAuthorizationConsentRepository authorizationConsentRepository) {
        this.authorizationConsentRepository = authorizationConsentRepository;
    }

    @Override
    public BaseRepository<HerodotusAuthorizationConsent, HerodotusAuthorizationConsentId> getRepository() {
        return this.authorizationConsentRepository;
    }

    public Optional<HerodotusAuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName) {
        Optional<HerodotusAuthorizationConsent> result = this.authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(registeredClientId, principalName);
        log.trace("[Herodotus] |- HerodotusAuthorizationConsent Service findByRegisteredClientIdAndPrincipalName.");
        return result;
    }

    public void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName) {
        this.authorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(registeredClientId, principalName);
        log.trace("[Herodotus] |- HerodotusAuthorizationConsent Service deleteByRegisteredClientIdAndPrincipalName.");
    }
}
