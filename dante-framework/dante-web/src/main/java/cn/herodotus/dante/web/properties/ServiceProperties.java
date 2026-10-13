/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.web.properties;

import cn.herodotus.dante.core.domain.Scan;
import cn.herodotus.dante.web.constant.WebConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 服务级配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/10/11 21:59
 */
@ConfigurationProperties(prefix = WebConstants.PROPERTY_PREFIX_SERVICE)
public class ServiceProperties {

    /**
     * 服务方法扫描配置
     */
    private Scanner scanner = new Scanner();

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public static class Scanner {

        private Scan rest = new Scan();
        private Scan grpc = new  Scan();
        private Scan task = new  Scan();
        private Scan mcp = new  Scan();

        public Scan getRest() {
            return rest;
        }

        public void setRest(Scan rest) {
            this.rest = rest;
        }

        public Scan getGrpc() {
            return grpc;
        }

        public void setGrpc(Scan grpc) {
            this.grpc = grpc;
        }

        public Scan getTask() {
            return task;
        }

        public void setTask(Scan task) {
            this.task = task;
        }

        public Scan getMcp() {
            return mcp;
        }

        public void setMcp(Scan mcp) {
            this.mcp = mcp;
        }
    }
}
