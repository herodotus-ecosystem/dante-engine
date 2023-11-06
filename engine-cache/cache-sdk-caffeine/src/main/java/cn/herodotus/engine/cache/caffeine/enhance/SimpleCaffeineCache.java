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

package cn.herodotus.engine.cache.caffeine.enhance;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: Caffeine 简单封装 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/12 18:41
 */
public class SimpleCaffeineCache {

    private final Cache<String, String> cache;

    public SimpleCaffeineCache(Duration duration) {
        this.cache = Caffeine.newBuilder().expireAfterWrite(duration).build();
    }

    public SimpleCaffeineCache(long duration, TimeUnit unit) {
        this.cache = Caffeine.newBuilder().expireAfterWrite(duration, unit).build();
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.getIfPresent(key);
    }
}
