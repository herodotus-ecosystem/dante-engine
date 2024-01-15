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

import cn.herodotus.engine.assistant.definition.domain.base.Entity;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Description: Vue Router Meta 属性定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/14 16:51
 */
public class BaseMeta implements Entity {

    private String title;
    private String icon;
    @JsonProperty("isNotKeepAlive")
    private Boolean notKeepAlive = false;
    @JsonProperty("isIgnoreAuth")
    private Boolean ignoreAuth = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getNotKeepAlive() {
        return notKeepAlive;
    }

    public void setNotKeepAlive(Boolean notKeepAlive) {
        this.notKeepAlive = notKeepAlive;
    }

    public Boolean getIgnoreAuth() {
        return ignoreAuth;
    }

    public void setIgnoreAuth(Boolean ignoreAuth) {
        this.ignoreAuth = ignoreAuth;
    }
}
