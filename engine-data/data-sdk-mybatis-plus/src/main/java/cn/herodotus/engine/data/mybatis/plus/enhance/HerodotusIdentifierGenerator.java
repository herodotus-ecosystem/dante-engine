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

package cn.herodotus.engine.data.mybatis.plus.enhance;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.data.id.Snowflake;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 自定义Mybatis Plus ID 生成器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/28 15:52
 */
@Component
public class HerodotusIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        // 采用雪花算法获取id,时间回拨会存在重复,这里用随机数来减少重复的概率
        final Snowflake snowflake = IdUtil.getSnowflake(1, (int) (Math.random() * 20 + 1));
        return snowflake.next();
    }
}
