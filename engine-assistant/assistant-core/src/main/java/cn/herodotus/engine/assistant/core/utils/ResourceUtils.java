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

package cn.herodotus.engine.assistant.core.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * <p>Description: 资源文件处理工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/29 21:39
 */
public class ResourceUtils {

    private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);

    private static volatile ResourceUtils INSTANCE;

    private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver;

    private ResourceUtils() {
        this.pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    }

    private static ResourceUtils getInstance() {
        if (ObjectUtils.isEmpty(INSTANCE)) {
            synchronized (ResourceUtils.class) {
                if (ObjectUtils.isEmpty(INSTANCE)) {
                    INSTANCE = new ResourceUtils();
                }
            }
        }

        return INSTANCE;
    }

    private PathMatchingResourcePatternResolver getPathMatchingResourcePatternResolver() {
        return this.pathMatchingResourcePatternResolver;
    }

    private static PathMatchingResourcePatternResolver getResolver() {
        return getInstance().getPathMatchingResourcePatternResolver();
    }

    public static Resource getResource(String location) {
        return getResolver().getResource(location);
    }

    public static File getFile(String location) throws IOException {
        return getResource(location).getFile();
    }

    public static InputStream getInputStream(String location) throws IOException {
        return getResource(location).getInputStream();
    }

    public static String getFilename(String location) {
        return getResource(location).getFilename();
    }

    public static URI getURI(String location) throws IOException {
        return getResource(location).getURI();
    }

    public static URL getURL(String location) throws IOException {
        return getResource(location).getURL();
    }

    public static long contentLength(String location) throws IOException {
        return getResource(location).contentLength();
    }

    public static long lastModified(String location) throws IOException {
        return getResource(location).lastModified();
    }

    public static boolean exists(String location) {
        return getResource(location).exists();
    }

    public static boolean isFile(String location) {
        return getResource(location).isFile();
    }

    public static boolean isReadable(String location) {
        return getResource(location).isReadable();
    }

    public static boolean isOpen(String location) {
        return ResourceUtils.getResource(location).isOpen();
    }

    public static Resource[] getResources(String locationPattern) throws IOException {
        return getResolver().getResources(locationPattern);
    }

    public static boolean isUrl(String location) {
        return org.springframework.util.ResourceUtils.isUrl(location);
    }

    public static boolean isClasspathUrl(String location) {
        return StringUtils.startsWith(location, ResourceLoader.CLASSPATH_URL_PREFIX);
    }

    public static boolean isClasspathAllUrl(String location) {
        return StringUtils.startsWith(location, ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX);
    }

    public static boolean isJarUrl(URL url) {
        return org.springframework.util.ResourceUtils.isJarURL(url);
    }

    public static boolean isFileUrl(URL url) {
        return org.springframework.util.ResourceUtils.isFileURL(url);
    }

    /**
     * 将 {@link Resource} 转换为 byte
     *
     * @param resource 资源 {@link Resource}
     * @return byte 数组
     */
    public static byte[] toBytes(Resource resource) {
        try {
            InputStream inputStream = resource.getInputStream();
            return FileCopyUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            log.error("[Herodotus] |- Converter resource to byte[] error!", e);
            return null;
        }
    }

    /**
     * 将 {@link Resource} 转换为 Base64 数据。
     * <p>
     * 例如：将图片类型的 Resource 转换为可以直接在前端展现的 Base64 数据
     *
     * @param resource 资源 {@link Resource}
     * @return Base64 类型的字符串
     */
    public static String toBase64(Resource resource) {
        byte[] bytes = toBytes(resource);
        return Base64.encode(bytes);
    }
}
