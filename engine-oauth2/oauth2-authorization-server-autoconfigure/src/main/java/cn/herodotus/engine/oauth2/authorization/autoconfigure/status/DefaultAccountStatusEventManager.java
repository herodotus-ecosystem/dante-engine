/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.engine.oauth2.authorization.autoconfigure.status;

import cn.herodotus.stirrup.core.foundation.context.ServiceContextHolder;
import cn.herodotus.engine.message.core.logic.strategy.AccountStatusEventManager;
import cn.herodotus.engine.message.core.logic.domain.UserStatus;
import cn.herodotus.engine.message.core.logic.event.ChangeUserStatusEvent;
import cn.herodotus.engine.oauth2.resource.autoconfigure.bus.RemoteChangeUserStatusEvent;

/**
 * <p>Description: 用户状态变更处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/10 17:25
 */
public class DefaultAccountStatusEventManager implements AccountStatusEventManager {
    @Override
    public String getDestinationServiceName() {
        return ServiceContextHolder.getInstance().getUpmsServiceName();
    }

    @Override
    public void postLocalProcess(UserStatus data) {
        publishEvent(new ChangeUserStatusEvent(data));
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteChangeUserStatusEvent(data, originService, destinationService));
    }
}
