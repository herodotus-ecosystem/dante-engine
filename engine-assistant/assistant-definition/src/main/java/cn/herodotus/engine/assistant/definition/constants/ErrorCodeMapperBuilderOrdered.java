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

package cn.herodotus.engine.assistant.definition.constants;

/**
 * <p>Description: 错误码构建顺序 </p>
 * <p>
 * 注解@Order或者接口Ordered的作用是定义Spring IOC容器中Bean的执行顺序的优先级，而不是定义Bean的加载顺序，Bean的加载顺序不受@Order或Ordered接口的影响
 *
 * @author : gengwei.zheng
 * @date : 2023/9/26 21:20
 */
public interface ErrorCodeMapperBuilderOrdered {

    int STEP = 10;

    int STANDARD = 0;
    int CACHE = STANDARD + STEP;
    int CAPTCHA = CACHE + STEP;
    int OAUTH2 = CAPTCHA + STEP;
    int REST = OAUTH2 + STEP;
    int MESSAGE = REST + STEP;
    int ACCESS = MESSAGE + STEP;
    int OSS = ACCESS + STEP;
}
