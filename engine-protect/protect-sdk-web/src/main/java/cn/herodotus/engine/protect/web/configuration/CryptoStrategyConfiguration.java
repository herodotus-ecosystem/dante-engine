/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.protect.web.configuration;

import cn.herodotus.engine.protect.core.definition.AsymmetricCryptoProcessor;
import cn.herodotus.engine.protect.core.definition.SymmetricCryptoProcessor;
import cn.herodotus.engine.protect.web.annotation.ConditionalOnSMCrypto;
import cn.herodotus.engine.protect.web.annotation.ConditionalOnStandardCrypto;
import cn.herodotus.engine.protect.web.crypto.processor.AESCryptoProcessor;
import cn.herodotus.engine.protect.web.crypto.processor.RSACryptoProcessor;
import cn.herodotus.engine.protect.web.crypto.processor.SM2CryptoProcessor;
import cn.herodotus.engine.protect.web.crypto.processor.SM4CryptoProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * <p>Description: 非对称算法配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/2 15:26
 */
@Configuration(proxyBeanMethods = false)
public class CryptoStrategyConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CryptoStrategyConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [Protect Crypto Strategy] Auto Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnSMCrypto
    static class SMCryptoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public AsymmetricCryptoProcessor sm2CryptoProcessor() {
            SM2CryptoProcessor sm2CryptoProcessor = new SM2CryptoProcessor();
            log.trace("[Herodotus] |- Strategy [SM Asymmetric SM2 Crypto Processor] Auto Configure.");
            return sm2CryptoProcessor;
        }

        @Bean
        @ConditionalOnMissingBean
        public SymmetricCryptoProcessor sm4CryptoProcessor() {
            SM4CryptoProcessor sm4CryptoProcessor = new SM4CryptoProcessor();
            log.trace("[Herodotus] |- Strategy [SM Symmetric SM4 Crypto Processor] Auto Configure.");
            return sm4CryptoProcessor;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnStandardCrypto
    static class StandardCryptoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public AsymmetricCryptoProcessor rsaCryptoProcessor() {
            RSACryptoProcessor rsaCryptoProcessor = new RSACryptoProcessor();
            log.trace("[Herodotus] |- Strategy [Standard Asymmetric RSA Crypto Processor] Auto Configure.");
            return rsaCryptoProcessor;
        }

        @Bean
        @ConditionalOnMissingBean
        public SymmetricCryptoProcessor aesCryptoProcessor() {
            AESCryptoProcessor aesCryptoProcessor = new AESCryptoProcessor();
            log.trace("[Herodotus] |- Strategy [Standard Symmetric AES Crypto Processor] Auto Configure.");
            return aesCryptoProcessor;
        }
    }
}
