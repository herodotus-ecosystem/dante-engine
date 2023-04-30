/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.engine.oauth2.authentication.provider;

import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Map;

/**
 * <p>Description: 设备 Client 认证 Token </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/26 23:40
 */
@Transient
public class OAuth2DeviceClientAuthenticationToken extends OAuth2ClientAuthenticationToken {

    public OAuth2DeviceClientAuthenticationToken(String clientId, ClientAuthenticationMethod clientAuthenticationMethod, Object credentials, Map<String, Object> additionalParameters) {
        super(clientId, clientAuthenticationMethod, credentials, additionalParameters);
    }

    public OAuth2DeviceClientAuthenticationToken(RegisteredClient registeredClient, ClientAuthenticationMethod clientAuthenticationMethod, Object credentials) {
        super(registeredClient, clientAuthenticationMethod, credentials);
    }
}
