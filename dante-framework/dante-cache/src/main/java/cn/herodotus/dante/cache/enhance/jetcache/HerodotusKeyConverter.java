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

package cn.herodotus.dante.cache.enhance.jetcache;

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.core.context.TenantContextHolder;
import cn.herodotus.dante.core.domain.cache.HiberanteQueryKeyWrapper;
import cn.herodotus.dante.core.domain.cache.HibernateCacheKeyWrapper;
import cn.hutool.v7.crypto.SecureUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.util.function.Function;

/**
 * <p>Description: 自定义 JetCache KeyConverter </p>
 * <p>
 * 主要为了实现基于 JetCache 的 Hibernate 二级缓存扩展。解决 Hibernate 缓存 Object 类型 Key 转换为 JetCache 可使用的 Key
 *
 * @author : gengwei.zheng
 * @date : 2025/12/6 12:58
 */
public class HerodotusKeyConverter implements Function<Object, Object> {

    private static final Logger log = LoggerFactory.getLogger(HerodotusKeyConverter.class);

    private final ObjectMapper objectMapper;

    public HerodotusKeyConverter() {
        // Hibernate QueryKey 对象没有 Get 和 Set 方法，常规序列化只会得到一个 {}。
        // 所以要单独处理，让 Jackson 根据属性进行序列化
        this.objectMapper = JsonMapper.builder()
                .changeDefaultVisibility(visibility -> visibility.withVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY))
                .build();
    }

    @Override
    public Object apply(Object originalKey) {
        if (originalKey == null) {
            return null;
        }

        // 常规String类型内容，非自定义 Hibernate 相关String类型内容，无需添加 TenantId
        if (originalKey instanceof String) {
            return originalKey;
        }

        // 自定义 Hibernate 相关String类型内容，需添加 TenantId
        // 通过 HibernateCacheKeyWrapper 与常规 String 类型内容进行区分
        if (originalKey instanceof HibernateCacheKeyWrapper cacheKeyWrapper) {
            return createKey(cacheKeyWrapper.getKey());
        }

        if (originalKey instanceof HiberanteQueryKeyWrapper queryKeyWrapper) {
            // 将 Hibernate QueryKey 对象转成 JSON，解决分页查询缓存问题。
            // 原有 QueryKey 只有一个 hashcode 方法可用，但 Hibernate 团段将这个方法中的 firstRow 和 maxRow 注释掉了，无法区分到底是那一页的查询。
            // 与 Hibernate 团队沟通多次，不同意修改。所以转成 JSON 是目前最合理的解决办法。
            // 之前版本是手动修改 QueryKey 代码然后覆盖原始代码，因为依赖 hibernate-core 的模块多，这种方式需要在很多模块复制相同的代码，不够优雅。
            String json = converterKeyToJson(queryKeyWrapper.getKey());

            log.trace("[Herodotus] |- HerodotusKeyConverter convert queryKey to json is : [{}]", json);

            if (StringUtils.isNotBlank(json)) {
                String storedKey = SecureUtil.md5(json);
                return createKey(storedKey);
            }
        }

        String key = SecureUtil.md5(converterKeyToJson(originalKey));
        log.trace("[Herodotus] |- HerodotusKeyConverter convert originalKey [{}] to key is : [{}]", originalKey,  key);
        return key;
    }

    private String createKey(String storedKey) {
        return getTenantId() + SymbolConstants.COLON + storedKey;
    }

    private String getTenantId() {
        String tenantId = TenantContextHolder.getTenantId();
        log.trace("[Herodotus] |- Tenant identifier for jpa second level cache is : [{}]", tenantId);
        return StringUtils.toRootLowerCase(tenantId);
    }

    private String converterKeyToJson(Object value) {
        try {
            return this.objectMapper.writeValueAsString(value);
        } catch (JacksonException e) {
            log.error("[Herodotus] |- Herodotus KeyConverter error, when to json! {}", e.getMessage());
            return null;
        }
    }
}
