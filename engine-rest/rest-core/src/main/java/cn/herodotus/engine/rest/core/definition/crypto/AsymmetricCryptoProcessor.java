/*
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君) (herodotus@aliyun.com & www.herodotus.cn)
 *
 * Dante Engine licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.gnu.org/licenses/lgpl.html>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.herodotus.engine.rest.core.definition.crypto;

import cn.herodotus.engine.assistant.core.domain.SecretKey;

/**
 * <p>Description: 非对称加密 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/5/1 15:06
 */
public interface AsymmetricCryptoProcessor {

    /**
     * 创建非对称算法，公钥私钥对。
     *
     * @return 非对称算法，公钥私钥对
     */
    SecretKey createSecretKey();


    /**
     * 用私钥解密
     *
     * @param privateKey 非对称算法 KeyPair 私钥
     * @param content    待解密数据
     * @return 解密后的数据
     */
    String decrypt(String content, String privateKey);

    /**
     * 用公钥加密
     *
     * @param publicKey 非对称算法 KeyPair 公钥
     * @param content   待加密数据
     * @return 加密后的数据
     */
    String encrypt(String content, String publicKey);
}
