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

package cn.herodotus.stirrup.core.foundation.enums;

/**
 * <p> Description : 用于区分是单体应用还是微服务应用 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/26 11:33
 */
public enum Architecture {

    /**
     * 分布式架构
     */
    DISTRIBUTED,

    /**
     * 单体式架构
     */
    MONOCOQUE;
}