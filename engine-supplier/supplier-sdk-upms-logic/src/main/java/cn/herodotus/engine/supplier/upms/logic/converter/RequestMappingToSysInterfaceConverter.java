/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.supplier.upms.logic.converter;

import cn.herodotus.engine.message.core.logic.domain.RequestMapping;
import cn.herodotus.engine.supplier.upms.logic.entity.security.SysInterface;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: RequestMapping 转 SysInterface 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/23 17:15
 */
public class RequestMappingToSysInterfaceConverter implements Converter<RequestMapping, SysInterface> {

    @Override
    public SysInterface convert(RequestMapping requestMapping) {
        SysInterface sysInterface = new SysInterface();
        sysInterface.setInterfaceId(requestMapping.getMappingId());
        sysInterface.setInterfaceCode(requestMapping.getMappingCode());
        sysInterface.setRequestMethod(requestMapping.getRequestMethod());
        sysInterface.setServiceId(requestMapping.getServiceId());
        sysInterface.setClassName(requestMapping.getClassName());
        sysInterface.setMethodName(requestMapping.getMethodName());
        sysInterface.setUrl(requestMapping.getUrl());
        sysInterface.setDescription(requestMapping.getDescription());
        return sysInterface;
    }
}
