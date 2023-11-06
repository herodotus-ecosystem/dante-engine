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

package cn.herodotus.engine.access.wxapp.enums;

/**
 * <p>Description: 跳转小程序类型 </p>
 * <p>
 * developer为开发版；trial为体验版；formal为正式版；默认为正式版
 *
 * @author : gengwei.zheng
 * @date : 2021/4/9 16:09
 */
public enum MiniProgramState {

    /**
     * 开发版
     */
    developer,

    /**
     * 体验版
     */
    trial,

    /**
     * 正式版
     */
    formal;
}
