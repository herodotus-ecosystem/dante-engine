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

package cn.herodotus.engine.cache.core.exception;

/**
 * <p>Description: 超出最大数量限制 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/7/6 23:03
 */
public class MaximumLimitExceededException extends Exception {

    public MaximumLimitExceededException() {
        super();
    }

    public MaximumLimitExceededException(String message) {
        super(message);
    }

    public MaximumLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaximumLimitExceededException(Throwable cause) {
        super(cause);
    }

    protected MaximumLimitExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
