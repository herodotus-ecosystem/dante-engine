/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl-3.0.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.data.core.entity;

import cn.herodotus.engine.assistant.definition.constants.DefaultConstants;
import cn.herodotus.engine.assistant.definition.domain.base.AbstractEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * <p>Description: DISCRIMINATOR 类型多租户实体基础类 </p>
 * <p>
 * Dante Cloud 系统本身如果要改成多租户模式，还是建议使用 DATABASE 模式。
 * 根据需要指定具体是哪些实体（数据表）采用 DISCRIMINATOR 模式多租户。
 * 不要什么实体都加以防产生不必要的干扰。
 *
 * @author : gengwei.zheng
 * @date : 2023/3/29 10:46
 */
@MappedSuperclass
public abstract class BaseTenantEntity extends AbstractEntity {

    @Schema(name = "租户ID", description = "Partitioned 类型租户ID")
    @Column(name = "tenant_id", length = 20)
    private String tenantId = DefaultConstants.TENANT_ID;
}
