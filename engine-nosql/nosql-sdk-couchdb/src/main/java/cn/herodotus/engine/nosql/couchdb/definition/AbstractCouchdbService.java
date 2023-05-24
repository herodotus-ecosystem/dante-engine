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
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.nosql.couchdb.definition;

import cn.herodotus.engine.assistant.core.definition.AbstractRest;
import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import cn.herodotus.engine.nosql.couchdb.properties.CouchdbProperties;
import cn.hutool.core.codec.Base64;
import org.apache.hc.core5.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: CouchDB 基础服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/8/27 15:33
 */
public abstract class AbstractCouchdbService extends AbstractRest {

    private static final Logger log = LoggerFactory.getLogger(AbstractCouchdbService.class);
    @Autowired
    private CouchdbProperties couchdbProperties;

    @Override
    protected String getBaseUrl() {
        return couchdbProperties.getEndpoint();
    }

    private String getBasicToken() {
        Assert.hasText(couchdbProperties.getUsername(), "username cannot be empty");
        Assert.hasText(couchdbProperties.getPassword(), "password cannot be empty");
        String content = couchdbProperties.getUsername() + SymbolConstants.COLON + couchdbProperties.getPassword();
        String token = BaseConstants.BASIC_TOKEN + Base64.encode(content);
        log.debug("[Herodotus] |- Create CouchDB Basic Authentication Token : [{}]", token);
        return token;
    }

    protected Map<String, String> getBasicAuthentication() {
        Map<String, String> header = new HashMap<>();
        header.put(HttpHeaders.AUTHORIZATION, getBasicToken());
        return header;
    }
}
