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

package cn.herodotus.engine.assistant.definition.support;

import cn.herodotus.engine.assistant.definition.domain.Pool;
import cn.herodotus.engine.assistant.definition.exception.BorrowObjectFromPoolErrorException;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 对象池抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/11/6 13:36
 */
public abstract class AbstractObjectPool<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractObjectPool.class);

    private final GenericObjectPool<T> genericObjectPool;

    protected AbstractObjectPool(@Nonnull PooledObjectFactory<T> pooledObjectFactory, @Nonnull Pool pool) {
        GenericObjectPoolConfig<T> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(pool.getMaxTotal());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        config.setMaxWait(pool.getMaxWait());
        config.setMinEvictableIdleDuration(pool.getMinEvictableIdleDuration());
        config.setSoftMinEvictableIdleDuration(pool.getSoftMinEvictableIdleDuration());
        config.setLifo(pool.getLifo());
        config.setBlockWhenExhausted(pool.getBlockWhenExhausted());
        genericObjectPool = new GenericObjectPool<>(pooledObjectFactory, config);
    }

    public T get() {
        try {
            T object = genericObjectPool.borrowObject();
            log.debug("[Herodotus] |- Fetch object from object pool.");
            return object;
        } catch (Exception e) {
            log.error("[Herodotus] |- Can not fetch object from pool.", e);
            throw new BorrowObjectFromPoolErrorException("Can not fetch object from pool.");
        }
    }

    public void close(T client) {
        if (ObjectUtils.isNotEmpty(client)) {
            log.debug("[Herodotus] |- Close object in pool.");
            genericObjectPool.returnObject(client);
        }
    }
}
