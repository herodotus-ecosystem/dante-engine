/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.stirrup.web.core.utils;

import cn.herodotus.stirrup.core.definition.domain.Result;
import cn.herodotus.stirrup.core.foundation.json.jackson2.utils.Jackson2Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class WebFluxUtils {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    public static PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    /**
     * 判断路径是否与路径模式匹配
     *
     * @param patterns 路径模式数组
     * @param path     url
     * @return 是否匹配
     */
    public static boolean isPathMatch(String[] patterns, String path) {
        return isPathMatch(Arrays.asList(patterns), path);
    }

    /**
     * 判断路径是否与路径模式匹配
     *
     * @param patterns 路径模式字符串List
     * @param path     url
     * @return 是否匹配
     */
    public static boolean isPathMatch(List<String> patterns, String path) {
        PathMatcher pathMatcher = getPathMatcher();
        for (String pattern : patterns) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    public static Mono<Void> writeJsonResponse(ServerHttpResponse response, Result<String> result) {
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatusCode(HttpStatus.valueOf(result.getStatus()));

        String jsonResult = Jackson2Utils.toJson(result);
        byte[] bytes = StringUtils.isNotBlank(jsonResult) ? jsonResult.getBytes(StandardCharsets.UTF_8) : new byte[]{};

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }

    public static boolean isJsonMediaType(String contentType) {
        return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType) || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(contentType);
    }
}
