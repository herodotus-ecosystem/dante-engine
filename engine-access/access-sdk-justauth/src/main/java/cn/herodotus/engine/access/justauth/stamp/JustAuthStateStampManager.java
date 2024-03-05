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

package cn.herodotus.engine.access.justauth.stamp;

import cn.herodotus.engine.access.core.constants.AccessConstants;
import cn.herodotus.engine.access.justauth.properties.JustAuthProperties;
import cn.herodotus.engine.cache.jetcache.stamp.AbstractStampManager;
import me.zhyd.oauth.cache.AuthStateCache;
import org.dromara.hutool.core.data.id.IdUtil;

import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 自定义JustAuth State Cache </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/5/22 10:22
 */
public class JustAuthStateStampManager extends AbstractStampManager<String, String> implements AuthStateCache {

    public JustAuthStateStampManager() {
        super(AccessConstants.CACHE_NAME_TOKEN_JUSTAUTH);
    }

    private JustAuthProperties justAuthProperties;

    public void setJustAuthProperties(JustAuthProperties justAuthProperties) {
        this.justAuthProperties = justAuthProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(justAuthProperties.getTimeout());
    }

    @Override
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }

    @Override
    public void cache(String key, String value) {
        this.put(key, value);
    }

    @Override
    public void cache(String key, String value, long expire) {
        this.put(key, value, expire, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean containsKey(String key) {
        return this.containKey(key);
    }
}
