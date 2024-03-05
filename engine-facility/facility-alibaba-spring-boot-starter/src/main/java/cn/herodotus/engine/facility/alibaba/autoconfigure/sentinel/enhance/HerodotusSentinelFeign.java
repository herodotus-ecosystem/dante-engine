/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.facility.alibaba.autoconfigure.sentinel.enhance;

import com.alibaba.cloud.sentinel.feign.SentinelContractHolder;
import feign.Contract;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.Target;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClientFactory;
import org.springframework.cloud.openfeign.FeignClientFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * {@link Feign.Builder}.
 *
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 * @author 黄学敏（huangxuemin)
 */
public final class HerodotusSentinelFeign {

    private static final String FEIGN_LAZY_ATTR_RESOLUTION = "spring.cloud.openfeign.lazy-attributes-resolution";

    private HerodotusSentinelFeign() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends Feign.Builder
            implements ApplicationContextAware {

        private Contract contract = new Contract.Default();

        private ApplicationContext applicationContext;

        private FeignClientFactory feignClientFactory;

        @Override
        public Feign.Builder invocationHandlerFactory(
                InvocationHandlerFactory invocationHandlerFactory) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Builder contract(Contract contract) {
            this.contract = contract;
            return this;
        }

        @Override
        public Feign build() {
            super.invocationHandlerFactory(new InvocationHandlerFactory() {
                @Override
                public InvocationHandler create(Target target,
                                                Map<Method, MethodHandler> dispatch) {
                    GenericApplicationContext gctx = (GenericApplicationContext) Builder.this.applicationContext;
                    BeanDefinition def = gctx.getBeanDefinition(target.type().getName());
                    FeignClientFactoryBean feignClientFactoryBean;

                    // If you need the attributes to be resolved lazily, set the property value to true.
                    Boolean isLazyInit = applicationContext.getEnvironment()
                            .getProperty(FEIGN_LAZY_ATTR_RESOLUTION, Boolean.class, false);
                    if (isLazyInit) {
                        /*
                         * Due to the change of the initialization sequence,
                         * BeanFactory.getBean will cause a circular dependency. So
                         * FeignClientFactoryBean can only be obtained from BeanDefinition
                         */
                        feignClientFactoryBean = (FeignClientFactoryBean) def
                                .getAttribute("feignClientsRegistrarFactoryBean");
                    } else {
                        feignClientFactoryBean = (FeignClientFactoryBean) applicationContext
                                .getBean("&" + target.type().getName());
                    }
                    Class fallback = feignClientFactoryBean.getFallback();
                    Class fallbackFactory = feignClientFactoryBean.getFallbackFactory();
                    String beanName = feignClientFactoryBean.getContextId();
                    if (!StringUtils.hasText(beanName)) {
                        beanName = (String) getFieldValue(feignClientFactoryBean, "name");
                    }

                    Object fallbackInstance;
                    FallbackFactory fallbackFactoryInstance;
                    // check fallback and fallbackFactory properties
                    if (void.class != fallback) {
                        fallbackInstance = getFromContext(beanName, "fallback", fallback,
                                target.type());
                        return new HerodotusSentinelInvocationHandler(target, dispatch, new FallbackFactory.Default(fallbackInstance));
                    }
                    if (void.class != fallbackFactory) {
                        fallbackFactoryInstance = (FallbackFactory) getFromContext(
                                beanName, "fallbackFactory", fallbackFactory,
                                FallbackFactory.class);
                        return new HerodotusSentinelInvocationHandler(target, dispatch, fallbackFactoryInstance);
                    }

                    // 默认的 fallbackFactory
                    HerodotusFallbackFactory herodotusFallbackFactory = new HerodotusFallbackFactory(target);
                    return new HerodotusSentinelInvocationHandler(target, dispatch, herodotusFallbackFactory);
                }

                private Object getFromContext(String name, String type,
                                              Class fallbackType, Class targetType) {
                    Object fallbackInstance = feignClientFactory.getInstance(name,
                            fallbackType);
                    if (fallbackInstance == null) {
                        throw new IllegalStateException(String.format(
                                "No %s instance of type %s found for feign client %s",
                                type, fallbackType, name));
                    }
                    // when fallback is a FactoryBean, should determine the type of instance
                    if (fallbackInstance instanceof FactoryBean<?> factoryBean) {
                        try {
                            fallbackInstance = factoryBean.getObject();
                        } catch (Exception e) {
                            throw new IllegalStateException(type + " create fail", e);
                        }
                        fallbackType = fallbackInstance.getClass();
                    }

                    if (!targetType.isAssignableFrom(fallbackType)) {
                        throw new IllegalStateException(String.format(
                                "Incompatible %s instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
                                type, fallbackType, targetType, name));
                    }
                    return fallbackInstance;
                }
            });

            super.contract(new SentinelContractHolder(contract));
            return super.build();
        }

        private Object getFieldValue(Object instance, String fieldName) {
            Field field = ReflectionUtils.findField(instance.getClass(), fieldName);
            field.setAccessible(true);
            try {
                return field.get(instance);
            } catch (IllegalAccessException e) {
                // ignore
            }
            return null;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext)
                throws BeansException {
            this.applicationContext = applicationContext;
            feignClientFactory = this.applicationContext.getBean(FeignClientFactory.class);
        }

    }

}
