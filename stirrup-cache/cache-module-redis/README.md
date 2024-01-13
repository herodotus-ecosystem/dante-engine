# Redis 相关代码组件模块

**包含以下内容：**

1. Redis 配置。
2. 扩展的 Redis Cache Manager 配置

# Redis功能

## 1. 添加依赖

```xml
<dependency>
    <groupId>cn.herodotus.stirrup</groupId>
    <artifactId>cache-module-redis</artifactId>
</dependency>
```
## 2. 配置说明

```yaml
spring:
  data:
    redis:
      host: 127.0.0.1
      port: 6795
      database: 0
      password:
      timeout: 10000
herodotus:
  cache:
    instances:
      # 缓存的键
      "sys:cache:user":
        # 缓存失效时间
        expire: 3h
```

## 3. 使用 `@EnableHerodotusRedis` 开启配置，这里自动配置了 `RedisTemplate<Object, Object>` 和 `StringRedisTemplate`
## 4. 提供了 `RedisBitMapUtils` 工具类，常规的 Redis 工具类需要自己封装