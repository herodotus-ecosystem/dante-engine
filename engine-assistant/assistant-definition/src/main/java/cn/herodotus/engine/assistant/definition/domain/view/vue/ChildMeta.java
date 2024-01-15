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

package cn.herodotus.engine.assistant.definition.domain.view.vue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Description: 子节点 Meta </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/14 16:54
 */
public class ChildMeta extends BaseMeta {

    @JsonProperty("isDetailContent")
    private Boolean detailContent;

    public Boolean getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(Boolean detailContent) {
        this.detailContent = detailContent;
    }
}
