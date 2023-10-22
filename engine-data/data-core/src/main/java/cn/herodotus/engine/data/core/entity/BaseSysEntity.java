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

package cn.herodotus.engine.data.core.entity;

import cn.herodotus.engine.data.core.enums.DataItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <p>Description: 框架基础权限实体通用基础类</p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 15:05
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseSysEntity extends BaseEntity {

    @Schema(name = "数据状态")
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private DataItemStatus status = DataItemStatus.ENABLE;

    @Schema(name = "是否为保留数据", description = "True 为不能删，False为可以删除")
    @Column(name = "is_reserved")
    private Boolean reserved = Boolean.FALSE;

    @Schema(name = "版本号")
    @Column(name = "reversion")
    private Integer reversion = 0;

    /**
     * 角色描述,UI界面显示使用
     */
    @Schema(name = "备注")
    @Column(name = "description", length = 512)
    private String description;

    public DataItemStatus getStatus() {
        return status;
    }

    public void setStatus(DataItemStatus status) {
        this.status = status;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReversion() {
        return reversion;
    }

    public void setReversion(Integer reversion) {
        this.reversion = reversion;
    }
}
