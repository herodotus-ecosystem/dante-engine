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

package cn.herodotus.engine.cache.core.enums;

/**
 * <p>Description: 缓存方式 </p>
 * <p>
 * 额外增加一个枚举类，避免直接引用 JetCache CacheType 带来过多不必要的引用。
 *
 * @author : gengwei.zheng
 * @date : 2023/5/19 20:25
 */
public enum CacheMethod {
    REMOTE,
    LOCAL,
    BOTH;
}
