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

package cn.herodotus.engine.rest.core.definition.context;

import cn.herodotus.engine.assistant.core.context.ServiceContextHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * <p>Description: 抽象 JPA 实体变更 Listener</p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/11 18:12
 */
public abstract class AbstractApplicationContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    protected ApplicationContext getApplicationContext() {
        if (ObjectUtils.isEmpty(applicationContext)) {
            return ServiceContextHolder.getInstance().getApplicationContext();
        }
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void publishEvent(ApplicationEvent event) {
        this.getApplicationContext().publishEvent(event);
    }
}
