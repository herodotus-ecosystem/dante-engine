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

package cn.herodotus.engine.facility.alibaba.autoconfigure.sentinel.enhance;

import feign.Target;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * <p>Description: Feign 统一 Fallback 工厂 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/30 15:09
 */
public class HerodotusFallbackFactory<T> implements FallbackFactory<T> {

    private final Target<T> target;

    public HerodotusFallbackFactory(Target<T> target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T create(Throwable cause) {
        final Class<T> targetType = target.type();
        final String targetName = target.name();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetType);
        enhancer.setUseCache(true);
        enhancer.setCallback(new HerodotusFeignFallback<>(targetType, targetName, cause));
        return (T) enhancer.create();
    }

}
