/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.cache.redis.utils;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * <p>Description: Redis BitMap 工具类 </p>
 * <p>
 * · Redis的Bitmaps这个“数据结构”可以实现对位的操作。Bitmaps本身不是一种数据结构，实际上就是字符串，但是它可以对字符串的位进行操作
 * · 可把Bitmaps想象成一个以位为单位数组，数组中的每个单元只能存0或者1，数组的下标在bitmaps中叫做偏移量
 * · 单个bitmaps的最大长度是512MB，即2^32个比特位
 * <p>
 * bitmaps的最大优势是节省存储空间。比如在一个以自增id代表不同用户的系统中，我们只需要512MB空间就可以记录40亿用户的某个单一信息，相比mysql节省了大量的空间
 * <p>
 * · 有两种类型的位操作：一类是对特定bit位的操作，比如设置/获取某个特定比特位的值。另一类是批量bit位操作，例如在给定范围内统计为1的比特位个数
 * <p>
 * 1.1 优点：
 * <p>
 * 节省空间：通过一个bit位来表示某个元素对应的值或者状态，其中key就是对应元素的值。实际上8个bit可以组成一个Byte，所以是及其节省空间的。
 * <p>
 * 效率高：setbit和getbit的时间复杂度都是O(1)，其他位运算效率也高。
 * <p>
 * 1.2 缺点：
 * 本质上位只有0和1的区别，所以用位做业务数据记录，就不需要在意value的值。
 *
 * @author : gengwei.zheng
 * @date : 2022/12/29 17:41
 * @see <a herf="https://www.jianshu.com/p/305e65de1b13"></a>
 */
@Component
public class RedisBitMapUtils {

    private static StringRedisTemplate stringRedisTemplate;

    /**
     * 计算 Hash 值
     *
     * @param key bitmap结构的key
     * @return Hash 值
     */
    private static long hash(String key) {
        return Math.abs(Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(StandardCharsets.UTF_8)).asInt());
    }

    /**
     * 设置与 param 对应的二进制位的值，{@param param}会经过hash计算进行存储。
     *
     * @param key   bitmap数据结构的key
     * @param param 要设置偏移的key，该key会经过hash运算。
     * @param value true：即该位设置为1，否则设置为0
     * @return 返回设置该value之前的值。
     */
    public static Boolean setBit(String key, String param, boolean value) {
        return stringRedisTemplate.opsForValue().setBit(key, hash(param), value);
    }

    /**
     * 查询与指定 param 对应二进制位的值，{@param param}会经过hash计算进行存储。
     *
     * @param key   bitmap结构的key
     * @param param 要移除偏移的key，该key会经过hash运算。
     * @return 若偏移位上的值为1，那么返回true。
     */
    public static boolean getBit(String key, String param) {
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().getBit(key, hash(param)));
    }

    /**
     * 将指定offset偏移量的值设置为1；
     *
     * @param key    bitmap结构的key
     * @param offset 指定的偏移量。
     * @param value  true：即该位设置为1，否则设置为0
     * @return 返回设置该value之前的值。
     */
    public static Boolean setBit(String key, Long offset, boolean value) {
        return stringRedisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 获取指定 offset 偏移量的值；
     *
     * @param key    bitmap结构的key
     * @param offset 指定的偏移量。
     * @return 若偏移位上的值为 1，那么返回true。
     */
    public static Boolean getBit(String key, long offset) {
        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 统计对应的bitmap上value为1的数量
     *
     * @param key bitmap的key
     * @return value等于1的数量
     */
    public static Long bitCount(String key) {
        return stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection.stringCommands().bitCount(key.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 统计指定范围中value为1的数量
     *
     * @param key   bitMap中的key
     * @param start 该参数的单位是byte（1byte=8bit），{@code setBit(key,7,true);}进行存储时，单位是bit。那么只需要统计[0,1]便可以统计到上述set的值。
     * @param end   该参数的单位是byte。
     * @return 在指定范围[start*8,end*8]内所有value=1的数量
     */
    public static Long bitCount(String key, int start, int end) {
        return stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection.stringCommands().bitCount(key.getBytes(), start, end));
    }

    /**
     * 对一个或多个保存二进制的字符串key进行元操作，并将结果保存到saveKey上。
     * <p>
     * bitop and saveKey key [key...]，对一个或多个key逻辑并，结果保存到saveKey。
     * bitop or saveKey key [key...]，对一个或多个key逻辑或，结果保存到saveKey。
     * bitop xor saveKey key [key...]，对一个或多个key逻辑异或，结果保存到saveKey。
     * bitop xor saveKey key，对一个或多个key逻辑非，结果保存到saveKey。
     * <p>
     *
     * @param op      元操作类型；
     * @param saveKey 元操作后将结果保存到saveKey所在的结构中。
     * @param destKey 需要进行元操作的类型。
     * @return 1：返回元操作值。
     */
    public static Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... destKey) {
        byte[][] bytes = new byte[destKey.length][];
        for (int i = 0; i < destKey.length; i++) {
            bytes[i] = destKey[i].getBytes();
        }
        return stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection.stringCommands().bitOp(op, saveKey.getBytes(), bytes));
    }

    /**
     * 对一个或多个保存二进制的字符串key进行元操作，并将结果保存到saveKey上，并返回统计之后的结果。
     *
     * @param op      元操作类型；
     * @param saveKey 元操作后将结果保存到saveKey所在的结构中。
     * @param destKey 需要进行元操作的类型。
     * @return 返回saveKey结构上value=1的所有数量值。
     */
    public static Long bitOpResult(RedisStringCommands.BitOperation op, String saveKey, String... destKey) {
        bitOp(op, saveKey, destKey);
        return bitCount(saveKey);
    }

    @Autowired
    @Qualifier(value = "stringRedisTemplate")
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisBitMapUtils.stringRedisTemplate = stringRedisTemplate;
    }
}
