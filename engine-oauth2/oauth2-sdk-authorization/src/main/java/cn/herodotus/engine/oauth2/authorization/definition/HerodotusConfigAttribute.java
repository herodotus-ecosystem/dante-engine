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

package cn.herodotus.engine.oauth2.authorization.definition;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 自定义SecurityConfig </p>
 * <p>
 * 自定义SecurityConfig，主要为了构建无参数构造函数，以解决序列化出错问题
 *
 * @author : gengwei.zheng
 * @date : 2021/9/11 15:57
 */
public class HerodotusConfigAttribute implements ConfigAttribute {

    private String attribute;

    public HerodotusConfigAttribute() {
    }

    public HerodotusConfigAttribute(String config) {
        Assert.hasText(config, "You must provide a configuration attribute");
        this.attribute = config;
    }

    public static HerodotusConfigAttribute create(String attribute) {
        Assert.notNull(attribute, "You must supply an array of attribute names");
        return new HerodotusConfigAttribute(attribute.trim());
    }

    public static List<ConfigAttribute> createListFromCommaDelimitedString(String access) {
        return createList(StringUtils.commaDelimitedListToStringArray(access));
    }

    public static List<ConfigAttribute> createList(String... attributeNames) {
        Assert.notNull(attributeNames, "You must supply an array of attribute names");
        List<ConfigAttribute> attributes = new ArrayList<>(attributeNames.length);
        for (String attribute : attributeNames) {
            attributes.add(new HerodotusConfigAttribute(attribute.trim()));
        }
        return attributes;
    }

    public String getAttribute() {
        return this.attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusConfigAttribute that = (HerodotusConfigAttribute) o;
        return Objects.equal(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attribute);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attrib", attribute)
                .toString();
    }
}
