/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
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
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.message.socketio.configuration;

import cn.herodotus.engine.message.socketio.properties.SocketIOProperties;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/12/4 17:49
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SocketIOProperties.class)
public class SocketIOConfiguration {

    @Bean
    public SocketIOServer socketIOServer(SocketIOProperties socketIOProperties) {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);

        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();

        configuration.setHostname(socketIOProperties.getHostname());
        configuration.setPort(socketIOProperties.getPort());
        configuration.setBossThreads(socketIOProperties.getBossCount());
        configuration.setWorkerThreads(socketIOProperties.getWorkCount());
        configuration.setPingInterval(socketIOProperties.getPingInterval());
        configuration.setPingTimeout(socketIOProperties.getPingTimeout());
        configuration.setUpgradeTimeout(socketIOProperties.getUpgradeTimeout());
        configuration.setMaxHttpContentLength(socketIOProperties.getMaxHttpContentLength());
        configuration.setMaxFramePayloadLength(socketIOProperties.getMaxFramePayloadLength());
        configuration.setAllowCustomRequests(socketIOProperties.getAllowCustomRequests());
        configuration.setSocketConfig(socketConfig);

        return new SocketIOServer(configuration);
    }

    /**
     * 用于扫描netty-socketio的注解，比如 @OnConnect、@OnEvent
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
        return new SpringAnnotationScanner(socketIOServer);
    }
}
