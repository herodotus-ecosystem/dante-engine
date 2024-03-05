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

package cn.herodotus.engine.assistant.core.constants;

/**
 * <p>Description: 常用正则表达式 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/12 10:43
 */
public interface RegexPool extends org.dromara.hutool.core.regex.RegexPool {

    /**
     * 匹配大括号以及其中的内容，
     * <p>
     * 示例： "ab{gnfnm}ah{hell}o"，匹配结果：{gnfnm}、{hell}
     */
    String BRACES_AND_CONTENT = "\\{([^}])*\\}";

    /**
     * 匹配所有字符
     * <p>
     * 示例：String cat = "abc", cat.split((?!^)) 匹配结果： array["a", "b", "c"]
     */
    String ALL_CHARACTERS = "(?!^)";

    /**
     * 单引号字符串等式
     * <p>
     * 示例：pattern='/open/**'  匹配结果：pattern 和 /open/**
     */
    String SINGLE_QUOTE_STRING_EQUATION = "(\\w+)\\s*=\\s*'(.*?)'";

    /**
     * Bucket DNS 兼容
     */
    String DNS_COMPATIBLE = "^[a-z0-9][a-z0-9\\.\\-]+[a-z0-9]$";

}
