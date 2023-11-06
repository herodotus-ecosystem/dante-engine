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

package cn.herodotus.engine.rest.core.definition.context;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 实体集合属性变更监听器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/11 18:12
 */
public abstract class AbstractCollectionChangeContext extends AbstractApplicationContextAware {

    private List<String> before;
    private List<String> after;

    public void setBefore(List<String> before) {
        this.before = before;
    }

    public void setAfter(List<String> after) {
        this.after = after;
    }

    protected List<String> getChangedItems() {
        if (CollectionUtils.isNotEmpty(this.before) && CollectionUtils.isNotEmpty(this.after)) {
            return new ArrayList<>(CollectionUtils.disjunction(this.before, this.after));
        }

        if (CollectionUtils.isNotEmpty(this.before) && CollectionUtils.isEmpty(this.after)) {
            return this.before;
        }

        if (CollectionUtils.isEmpty(this.before) && CollectionUtils.isNotEmpty(this.after)) {
            return this.after;
        }

        return new ArrayList<>();
    }
}
