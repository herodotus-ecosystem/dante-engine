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

package cn.herodotus.engine.oauth2.management.controller;

import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.data.core.service.WriteableService;
import cn.herodotus.engine.oauth2.management.converter.OAuth2DeviceToDtoConverter;
import cn.herodotus.engine.oauth2.management.converter.OAuth2DeviceToEntityConverter;
import cn.herodotus.engine.oauth2.management.dto.OAuth2DeviceDto;
import cn.herodotus.engine.oauth2.management.entity.OAuth2Device;
import cn.herodotus.engine.oauth2.management.service.OAuth2DeviceService;
import cn.herodotus.engine.rest.core.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: OAuth2DeviceController </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/15 16:58
 */
@RestController
@RequestMapping("/authorize/device")
@Tags({
        @Tag(name = "OAuth2 认证服务接口"),
        @Tag(name = "物联网管理接口"),
        @Tag(name = "物联网设备接口")
})
public class OAuth2DeviceController extends BaseController<OAuth2Device, String> {

    private final OAuth2DeviceService deviceService;
    private final Converter<OAuth2Device, OAuth2DeviceDto> toDto;
    private final Converter<OAuth2DeviceDto, OAuth2Device> toEntity;

    public OAuth2DeviceController(OAuth2DeviceService deviceService) {
        this.deviceService = deviceService;
        this.toDto = new OAuth2DeviceToDtoConverter();
        this.toEntity = new OAuth2DeviceToEntityConverter();
    }

    @Override
    public WriteableService<OAuth2Device, String> getWriteableService() {
        return deviceService;
    }

    @Operation(summary = "获取OAuth2Device分页数据", description = "通过pageNumber和pageSize获取分页数据")
    @Parameters({
            @Parameter(name = "pageNumber", required = true, description = "当前页数"),
            @Parameter(name = "pageSize", required = true, description = "每页显示数据条目")
    })
    @GetMapping
    @Override
    public Result<Map<String, Object>> findByPage(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {

        Page<OAuth2Device> pages = deviceService.findByPage(pageNumber, pageSize);
        if (ObjectUtils.isNotEmpty(pages) && CollectionUtils.isNotEmpty(pages.getContent())) {
            List<OAuth2DeviceDto> auth2Devices = pages.getContent().stream().map(toDto::convert).collect(Collectors.toList());
            return result(getPageInfoMap(auth2Devices, pages.getTotalPages(), pages.getTotalElements()));
        }

        return Result.failure("查询数据失败！");
    }

    @Operation(summary = "保存或更新OAuth2设备", description = "接收JSON数据，转换为OauthClientDetails实体，进行更新")
    @Parameters({
            @Parameter(name = "domain", required = true, description = "可转换为OauthClientDetails实体的json数据", schema = @Schema(implementation = OAuth2DeviceDto.class))
    })
    @PostMapping
    public Result<OAuth2Device> saveOrUpdate(@RequestBody OAuth2DeviceDto domain) {
        OAuth2Device device = deviceService.saveAndFlush(toEntity.convert(domain));
        return result(device);
    }

    @Operation(summary = "删除OAuth2设备", description = "根据设备ID删除OAuth2设备，以及相关联的关系数据")
    @Parameters({
            @Parameter(name = "deviceId", required = true, description = "deviceId")
    })
    @DeleteMapping
    @Override
    public Result<String> delete(@RequestBody String deviceId) {
        deviceService.deleteById(deviceId);
        return Result.success("删除成功");
    }

    @Operation(summary = "给设备分配Scope", description = "给设备分配Scope")
    @Parameters({
            @Parameter(name = "deviceId", required = true, description = "设备ID"),
            @Parameter(name = "scopes[]", required = true, description = "Scope对象组成的数组")
    })
    @PutMapping
    public Result<OAuth2Device> authorize(@RequestParam(name = "deviceId") String deviceId, @RequestParam(name = "scopes[]") String[] scopes) {
        OAuth2Device device = deviceService.authorize(deviceId, scopes);
        return result(device);
    }
}
