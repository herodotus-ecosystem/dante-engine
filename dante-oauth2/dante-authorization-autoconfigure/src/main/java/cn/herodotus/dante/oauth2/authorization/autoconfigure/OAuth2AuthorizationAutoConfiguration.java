/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.oauth2.authorization.autoconfigure;

import cn.herodotus.dante.messaging.domain.AttributeCollector;
import cn.herodotus.dante.messaging.domain.SecurityAttribute;
import cn.herodotus.dante.messaging.processor.AttributeTransferProcessor;
import cn.herodotus.dante.oauth2.authorization.attribute.RestSecurityAttributeAnalyzer;
import cn.herodotus.dante.oauth2.authorization.attribute.RestSecurityAttributeStorage;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.listener.LocalAttributeCollectionListener;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.listener.RemoteAttributeCollectionListener;
import cn.herodotus.dante.oauth2.authorization.autoconfigure.strategy.DefaultAttributeCollectionEventManager;
import cn.herodotus.dante.oauth2.authorization.config.OAuth2ServletAuthorizationConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * <p>Description: OAuth2 资源服务器自动配置模块 </p>
 * <p>
 * 接口（资源服务器中提供的 REST API）聚合汇总，实现权限管控的主要逻辑：
 * 1. 各服务（资源服务器）启动完成之后，会自动执行 {@code RestMappingScanner} 对该服务中的 REST API 进行扫描，然后将扫描结果转换成 {@link SecurityAttribute},通过 {@link DefaultAttributeCollectionEventManager} 将数据以 Event（Local Event 或基于 Spring Cloud Bus 的 Remote Event）方式发送到接口权限管理服务（当前为 UPMS 服务）
 * 注意：
 * 1.1. 只有使用 Swagger {@link Operation} 注解标注过的 REST API 才会被扫描到。该措施主要为了强制编写 Swagger 说明
 * 1.2. UPMS 服务自身 REST API 也会进行聚合，但是不需要远程发送。
 * 1.3. {@link DefaultAttributeCollectionEventManager} 在发送 Event 时，会提前调用 {@link RestSecurityAttributeAnalyzer#postLocalResourceMatcherProcess()} 对服务本地配置的静态权限进行处理，放入到本地权限缓存中 {@link RestSecurityAttributeStorage}。
 * 2. UPMS 服务会使用 {@link LocalAttributeCollectionListener} 接收本地（UPMS自身）和使用 {@link RemoteAttributeCollectionListener} 接收其它服务的接口数据
 * 注意：单体架构就只有 {@link LocalAttributeCollectionListener} 会生效。 {@link RemoteAttributeCollectionListener} 依赖消息队列，仅在微服务架构下生效。
 * 3. UPMS 接收到各服务的扫描到的接口数据后，会调用 {@link AttributeTransferProcessor#storeAndMergeAttributeCollector(AttributeCollector, BiConsumer)} 方法，执行以下操作：
 * 3.1. 先将接口数据存入 {@code SysInterface} 表中。
 * 3.2. 查询 {@code SysInterface} 表中有的但是 {@code SysAttribute} 表中没有的数据，将这部分差异数据存入 {@code SysAttribute} 表中（注：该方法时为了规避 JPA 更新操作会全部字段覆盖同时兼顾性能的措施）
 * 3.3. 将 {@code SysAttribute} 中最新的数据，分发至指定的 serviceId 对应服务中（如果一个服务是多实例，只会又一个实例接收到分发数据，其它实例通过 JetCache 多级缓存的同步机制来同步数据，以此种方式来降低消息的发送，见：herodotus-cloud-kafka.yaml 中 Spring Cloud Stream 部分配置）
 * 4. 各个服务使用 {@link RestSecurityAttributeAnalyzer#postAttributeDistributionProcess(List)} 方法接收接口权限管理服务（当前为 UPMS 服务）返回的数据。该方法会执行以下操作：
 * 4.1. 按照接口数据的类型（全路径、占位符、通配符）三种类型进行分组
 * 4.2. 先将占位符、通配符类型接口存入 {@link RestSecurityAttributeStorage} 的 {@code compatible} 的缓存中
 * 4.3. 然后拿到所有的全路径接口，与占位符、通配符进行比较，去除重复可能产生冲突的权限
 * 4.4. 最后将去除冲突后的全路径接口存入 {@link RestSecurityAttributeStorage} 的 {@code indexable} 中，用于后续的鉴权使用
 * 5. {@code SysAttribute} 数据不管是 webExpression 还是 permissions 数据出现变换，均会通过 {@code SysAttributeEntityListener} 监听并发送 Event，触发新的权限数据分发
 *
 * @author : gengwei.zheng
 * @date : 2023/10/28 14:22
 */
@AutoConfiguration
@EnableAsync
@EnableMethodSecurity(proxyTargetClass = true, securedEnabled = true, jsr250Enabled = true)
@Import({
        OAuth2ServletAuthorizationConfiguration.class
})
public class OAuth2AuthorizationAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthorizationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Module [OAuth2 Resource Server Starter] Configure.");
    }
}
