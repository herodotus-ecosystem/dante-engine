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

package cn.herodotus.stirrup.access.sms.stamp;

import cn.herodotus.stirrup.cache.jetcache.stamp.AbstractStampManager;
import cn.herodotus.stirrup.access.core.constants.AccessConstants;
import cn.herodotus.stirrup.access.sms.properties.SmsProperties;
import org.dromara.hutool.core.util.RandomUtil;
import org.dromara.sms4j.api.dao.SmsDao;

import java.time.Duration;

/**
 * <p>Description: 手机短信验证码签章 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/26 17:44
 */
public class VerificationCodeStampManager extends AbstractStampManager<String, String> implements SmsDao {

    private SmsProperties smsProperties;

    public VerificationCodeStampManager() {
        super(AccessConstants.CACHE_NAME_TOKEN_VERIFICATION_CODE);
    }

    public void setSmsProperties(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public String nextStamp(String key) {
        if (smsProperties.getSandbox()) {
            return smsProperties.getTestCode();
        } else {
            return RandomUtil.randomNumbers(smsProperties.getLength());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(smsProperties.getExpire());
    }

    public Boolean getSandbox() {
        return smsProperties.getSandbox();
    }

    public String getVerificationCodeTemplateId() {
        return smsProperties.getVerificationCodeTemplateId();
    }

    @Override
    public void set(String key, Object value, long cacheTime) {
        put(key, String.valueOf(value), Duration.ofSeconds(cacheTime));
    }

    @Override
    public void set(String key, Object value) {
        put(key, String.valueOf(value));
    }

    @Override
    public void clean() {

    }
}
