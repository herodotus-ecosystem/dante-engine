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

package cn.herodotus.engine.assistant.core.json.jackson2;

/**
 * <p>Description: Jackson2 ObjectMapper builder Customizer 顺序控制 </p>
 * <p>
 * 值越小越先执行
 *
 * @author : gengwei.zheng
 * @date : 2023/4/29 16:30
 */
public interface Jackson2CustomizerOrder {

    int CUSTOMIZER_DEFAULT = 1;

    int CUSTOMIZER_XSS = CUSTOMIZER_DEFAULT + 1;
}
