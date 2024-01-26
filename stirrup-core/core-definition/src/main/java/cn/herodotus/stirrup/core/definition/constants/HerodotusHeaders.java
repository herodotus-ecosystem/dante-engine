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

package cn.herodotus.stirrup.core.definition.constants;

/**
 * <p>Description: 自定义 HttpHeader </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/1/26 17:48
 */
public interface HerodotusHeaders {

    String X_HERODOTUS_SESSION_ID = "X-Herodotus-Session-id";
    String X_HERODOTUS_FROM_IN = "X-Herodotus-From-In";
    String X_HERODOTUS_TENANT_ID = "X-Herodotus-Tenant-Id";
    String X_HERODOTUS_OPEN_ID = "X-Herodotus-Open-Id";
}
