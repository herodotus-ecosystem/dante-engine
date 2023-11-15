# 简介

Caffeine 是一个基于 Java8 开发的提供了近乎最佳命中率的高性能的缓存库。

用户手册：https://github.com/ben-manes/caffeine/wiki/Home-zh-CN

dante 对其进行了简单封装

# 使用说明

1. 引入资源

```xml
<dependency>
  <groupId>cn.herodotus.engine</groupId>
  <artifactId>cache-sdk-caffeine</artifactId>
</dependency>
```
2. 写一个配置类，导入配置

```java
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
@Import(CacheCaffeineConfiguration.class)
public class MyCacheConfiguration {
}
```

3. 一些可选配置项

```yaml
herodotus:
  cache:
    # 是否允许存储空值
    allowNullValues: true
    # 缓存名称转换分割符
    separator: '-'
    # 全局的过期时间
    expire: 2h
    # 本地缓存过期时间
    localExpire: 2h
    # 针对不同实体单独设置的过期时间，如果不设置，则统一使用默认时间
    instances: 
      # 缓存的键
      "sys:cache:user":
        # 缓存失效时间
        expire: 2h
```
4. 可使用spring-cache注解

```java
@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {

    @GetMapping("cacheAuto")
    @Cacheable(value="sys:cache", key="#key")
    public ResponseEntity<Long> testCacheCreate(String key) {
        // 模拟查询一个结果
        long millis = System.currentTimeMillis();
        return ResponseEntity.ok(millis);
    }

    @PostMapping("cacheAuto")
    @CachePut(value="sys:cache", key="#key")
    public ResponseEntity<Long> testCacheUpdate(String key) {
        // 模拟查询一个结果
        long millis = System.currentTimeMillis();
        return ResponseEntity.ok(millis);
    }

    @DeleteMapping("cacheAuto")
    @CacheEvict(value="sys:cache", key="#key")
    public ResponseEntity<String> testCacheDelete(String key) {
        // 模拟查询一个结果
        return ResponseEntity.ok("删除成功");
    }
}
```