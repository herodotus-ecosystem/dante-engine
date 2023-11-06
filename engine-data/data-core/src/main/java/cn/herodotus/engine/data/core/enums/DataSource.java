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

package cn.herodotus.engine.data.core.enums;

/**
 * <p>Description: 数据源 </p>
 * <p>
 * 越来越多的模块会涉及到多种数据源的切换，定义一个数据源类型的枚举方便以配置的方式实现数据源的切换
 *
 * @author : gengwei.zheng
 * @date : 2023/10/2 16:34
 */
public enum DataSource {
    JPA, MONGODB
}
