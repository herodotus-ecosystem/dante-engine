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

package cn.herodotus.engine.rest.core.validation;

import cn.herodotus.engine.rest.core.annotation.EnumeratedValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * <p>Description: 枚举值校验逻辑 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/6/13 15:58
 */
public class EnumeratedValueValidator implements ConstraintValidator<EnumeratedValue, Object> {

    private String[] names;
    private int[] ordinals;

    @Override
    public void initialize(EnumeratedValue constraintAnnotation) {
        names = constraintAnnotation.names();
        ordinals = constraintAnnotation.ordinals();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value instanceof String) {
            for (String name : names) {
                if (name.equals(value)) {
                    return true;
                }
            }
        } else if (value instanceof Integer) {
            for (int ordinal : ordinals) {
                if (ordinal == (Integer) value) {
                    return true;
                }
            }
        }
        return false;
    }
}
