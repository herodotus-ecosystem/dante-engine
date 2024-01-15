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

package cn.herodotus.engine.rest.core.definition.dto;

import cn.herodotus.engine.assistant.definition.domain.base.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: DTO基类定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/30 22:30
 */
public abstract class BaseDto extends AbstractDto {

    @Schema(title = "排序值")
    private Integer ranking = 0;

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
