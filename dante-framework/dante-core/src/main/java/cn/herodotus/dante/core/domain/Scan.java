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

package cn.herodotus.dante.core.domain;

import cn.herodotus.dante.core.constant.SystemConstants;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <p>Description: 应用启动扫描通用属性定义实体 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/10/10 17:31
 */
public class Scan {
    /**
     * 是否开启接口扫描
     */
    private Boolean enabled = true;
    /**
     * 指定扫描的命名空间。未指定的命名空间中，即使包含 RequestMapping，也不会被添加进来。
     */
    private List<String> groupIds;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getGroupIds() {
        List<String> defaultGroupIds = Stream.of(SystemConstants.PACKAGE_NAME).toList();

        if (CollectionUtils.isEmpty(this.groupIds)) {
            this.groupIds = new ArrayList<>();
        }

        this.groupIds.addAll(defaultGroupIds);
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }
}
