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

package cn.herodotus.dante.oauth2.authorization.matcher;

import cn.herodotus.dante.oauth2.authorization.attribute.RestSecurityAttributeStorage;
import cn.herodotus.dante.oauth2.commons.domain.HerodotusRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.util.ServletRequestPathUtils;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Objects;

/**
 * <p>Description: 自定义请求路径匹配器 </p>
 * <p>
 * 拷贝自 {@link PathPatternRequestMatcher}。进行一定的修改以支持在没有 {@link HttpServletRequest} 的环境下进行路径的比较和匹配
 * <p>
 * 概扩展的目的，主要是为了实现 {@link RestSecurityAttributeStorage} 代码中，使用 {@link PathPatternRequestMatcher} 逻辑进行高效请求路径去重分析。
 *
 * @author : gengwei.zheng
 * @date : 2025/8/23 23:58
 */
public class HerodotusPathPatternRequestMatcher implements HerodotusRequestMatcher {

    private final PathPattern pattern;

    private final HerodotusRequestMatcher method;

    /**
     * Creates a {@link HerodotusPathPatternRequestMatcher} that uses the provided {@code pattern}.
     * <p>
     * The {@code pattern} should be relative to the context path
     * </p>
     *
     * @param pattern the pattern used to match
     */
    private HerodotusPathPatternRequestMatcher(PathPattern pattern, HerodotusRequestMatcher method) {
        this.pattern = pattern;
        this.method = method;
    }

    /**
     * Use {@link PathPatternParser#defaultInstance} to parse path patterns.
     *
     * @return a {@link Builder} that treats URIs as relative to the context path, if any
     */
    public static Builder withDefaults() {
        return new Builder();
    }

    /**
     * Use this {@link PathPatternParser} to parse path patterns.
     *
     * @param parser the {@link PathPatternParser} to use
     * @return a {@link Builder} that treats URIs as relative to the given
     * {@code servletPath}
     */
    public static Builder withPathPatternParser(PathPatternParser parser) {
        Assert.notNull(parser, "pathPatternParser cannot be null");
        return new Builder(parser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        return matcher(request).isMatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchResult matcher(HttpServletRequest request) {
        if (!this.method.matches(request)) {
            return MatchResult.notMatch();
        }
        PathContainer path = getPathContainer(request);
        PathPattern.PathMatchInfo info = this.pattern.matchAndExtract(path);
        return (info != null) ? MatchResult.match(info.getUriVariables()) : MatchResult.notMatch();
    }

    @Override
    public boolean matches(HerodotusRequest request) {
        return matcher(request).isMatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchResult matcher(HerodotusRequest request) {
        if (!this.method.matches(request)) {
            return MatchResult.notMatch();
        }

        PathContainer path = getPathContainer(request.getPattern());
        PathPattern.PathMatchInfo info = this.pattern.matchAndExtract(path);
        return (info != null) ? MatchResult.match(info.getUriVariables()) : MatchResult.notMatch();
    }

    private PathContainer getPathContainer(HttpServletRequest request) {
        RequestPath path;
        if (ServletRequestPathUtils.hasParsedRequestPath(request)) {
            path = ServletRequestPathUtils.getParsedRequestPath(request);
        } else {
            path = ServletRequestPathUtils.parseAndCache(request);
            ServletRequestPathUtils.clearParsedRequestPath(request);
        }
        PathContainer contextPath = path.contextPath();
        return path.subPath(contextPath.elements().size());
    }

    private PathContainer getPathContainer(String uri) {
        return RequestPath.parse(uri, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HerodotusPathPatternRequestMatcher that)) {
            return false;
        }
        return Objects.equals(this.pattern, that.pattern) && Objects.equals(this.method, that.method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.pattern, this.method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder request = new StringBuilder();
        if (this.method instanceof HttpMethodRequestMatcher m) {
            request.append(m.method.name()).append(' ');
        }
        return "PathPattern [" + request + this.pattern + "]";
    }

    /**
     * A builder for specifying various elements of a request for the purpose of creating
     * a {@link HerodotusPathPatternRequestMatcher}.
     *
     * <p>
     * To match a request URI like {@code /app/servlet/my/resource/**} where {@code /app}
     * is the context path, you can do
     * {@code PathPatternRequestMatcher.withDefaults().matcher("/servlet/my/resource/**")}
     *
     * <p>
     * If you have many paths that have a common path prefix, you can use
     * {@link #basePath} to reduce repetition like so:
     *
     * <pre>
     *     PathPatternRequestMatcher.Builder mvc = withDefaults().basePath("/mvc");
     *     http
     *         .authorizeHttpRequests((authorize) -> authorize
     *              .requestMatchers(mvc.matcher("/user/**")).hasAuthority("user")
     *              .requestMatchers(mvc.matcher("/admin/**")).hasAuthority("admin")
     *         )
     *             ...
     * </pre>
     */
    public static final class Builder {

        private final PathPatternParser parser;

        private final String basePath;

        Builder() {
            this(PathPatternParser.defaultInstance);
        }

        Builder(PathPatternParser parser) {
            this(parser, "");
        }

        Builder(PathPatternParser parser, String basePath) {
            this.parser = parser;
            this.basePath = basePath;
        }

        /**
         * Match requests starting with this {@code basePath}.
         *
         * <p>
         * Prefixes should be of the form {@code /my/prefix}, starting with a slash, not
         * ending in a slash, and not containing and wildcards
         *
         * @param basePath the path prefix
         * @return the {@link Builder} for more configuration
         */
        public Builder basePath(String basePath) {
            Assert.notNull(basePath, "basePath cannot be null");
            Assert.isTrue(basePath.startsWith("/"), "basePath must start with '/'");
            Assert.isTrue(!basePath.endsWith("/"), "basePath must not end with a slash");
            Assert.isTrue(!basePath.contains("*"), "basePath must not contain a star");
            return new Builder(this.parser, basePath);
        }

        /**
         * Match requests having this path pattern.
         *
         * <p>
         * When the HTTP {@code method} is null, then the matcher does not consider the
         * HTTP method
         *
         * <p>
         * Path patterns always start with a slash and may contain placeholders. They can
         * also be followed by {@code /**} to signify all URIs under a given path.
         *
         * <p>
         * These must be specified relative to any context path prefix. A
         * {@link #basePath} may be specified to reuse a common prefix, for example a
         * servlet path.
         *
         * <p>
         * The following are valid patterns and their meaning
         * <ul>
         * <li>{@code /path} - match exactly and only `/path`</li>
         * <li>{@code /path/**} - match `/path` and any of its descendents</li>
         * <li>{@code /path/{value}/**} - match `/path/subdirectory` and any of its
         * descendents, capturing the value of the subdirectory in
         * {@link RequestAuthorizationContext#getVariables()}</li>
         * </ul>
         *
         * <p>
         * A more comprehensive list can be found at {@link PathPattern}.
         *
         * @param path the path pattern to match
         * @return the {@link Builder} for more configuration
         */
        public HerodotusPathPatternRequestMatcher matcher(String path) {
            return matcher(null, path);
        }

        /**
         * Match requests having this {@link HttpMethod} and path pattern.
         *
         * <p>
         * When the HTTP {@code method} is null, then the matcher does not consider the
         * HTTP method
         *
         * <p>
         * Path patterns always start with a slash and may contain placeholders. They can
         * also be followed by {@code /**} to signify all URIs under a given path.
         *
         * <p>
         * These must be specified relative to any context path prefix. A
         * {@link #basePath} may be specified to reuse a common prefix, for example a
         * servlet path.
         *
         * <p>
         * The following are valid patterns and their meaning
         * <ul>
         * <li>{@code /path} - match exactly and only `/path`</li>
         * <li>{@code /path/**} - match `/path` and any of its descendents</li>
         * <li>{@code /path/{value}/**} - match `/path/subdirectory` and any of its
         * descendents, capturing the value of the subdirectory in
         * {@link RequestAuthorizationContext#getVariables()}</li>
         * </ul>
         *
         * <p>
         * A more comprehensive list can be found at {@link PathPattern}.
         *
         * @param method the {@link HttpMethod} to match, may be null
         * @param path   the path pattern to match
         * @return the {@link Builder} for more configuration
         */
        public HerodotusPathPatternRequestMatcher matcher(@Nullable HttpMethod method, String path) {
            Assert.notNull(path, "pattern cannot be null");
            Assert.isTrue(path.startsWith("/"), "pattern must start with a /");
            PathPattern pathPattern = this.parser.parse(this.basePath + path);
            return new HerodotusPathPatternRequestMatcher(pathPattern,
                    (method != null) ? new HttpMethodRequestMatcher(method) : HerodotusAnyRequestMatcher.INSTANCE);
        }

        public HerodotusPathPatternRequestMatcher matcher(HerodotusRequest request) {
            HttpMethod method = null;
            if (StringUtils.isNotBlank(request.getHttpMethod())) {
                method = HttpMethod.valueOf(request.getHttpMethod());
            }
            return matcher(method, request.getPattern());
        }

    }

    private static final class HttpMethodRequestMatcher implements HerodotusRequestMatcher {

        private final HttpMethod method;

        HttpMethodRequestMatcher(HttpMethod method) {
            this.method = method;
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            return this.method.name().equals(request.getMethod());
        }

        @Override
        public boolean matches(HerodotusRequest request) {
            return Strings.CI.equals(this.method.name(), request.getHttpMethod());
        }

        @Override
        public String toString() {
            return "HttpMethod [" + this.method + "]";
        }

    }
}
