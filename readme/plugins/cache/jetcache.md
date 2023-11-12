# 简介

JetCache是一个基于Java的缓存系统封装，提供统一的API和注解来简化缓存的使用。 JetCache提供了比SpringCache更加强大的注解，可以原生的支持TTL、两级缓存、分布式自动刷新，还提供了Cache接口用于手工缓存操作。 当前有四个实现，RedisCache、TairCache（此部分未在github开源）、CaffeineCache(in memory)和一个简易的LinkedHashMapCache(in memory)，要添加新的实现也是非常简单的。

[中文文档](https://github.com/alibaba/jetcache/tree/master/docs/CN)

# 配置说明

```yaml
# 最简配置
jetcache:
  # 统计间隔，0表示不统计，单位分钟
  statIntervalMinutes: 15
  local:
    default:
      type: caffeine
      limit: 10000
  remote:
    default:
      type: redis.lettuce
      # jetcahe2.7的两级缓存支持更新以后失效其他JVM中的local cache，
      # 但多个服务共用redis同一个channel可能会造成广播风暴，需要在这里指定channel，
      # 你可以决定多个不同的服务是否共用同一个channel。如果没有指定则不开启。
      broadcastChannel: herodotus
      # 单机模式
      uri: redis://${spring.data.redis.password}@${spring.data.redis.host}:${spring.data.redis.port}/${spring.data.redis.database}
      # 哨兵模式
      #uri: redis-sentinel://127.0.0.1:26379,127.0.0.1:26380,127.0.0.1:26381/?sentinelMasterId=mymaster
      # 查看io.lettuce.core.ReadFrom
      #readFrom: slavePreferred
      # 集群模式
      #mode: cluster
      #uri:
#        - redis://127.0.0.1:7000
#        - redis://127.0.0.1:7001
#        - redis://127.0.0.1:7002
```

## local.${area}.type 详解

支持以下两种配置: 

- `linkedhashmap` (查看 `com.alicp.jetcache.autoconfigure.LinkedHashMapAutoConfiguration` 配置类) 
- `caffeine` (查看 `com.alicp.jetcache.autoconfigure.CaffeineAutoConfiguration` 配置类)。

> dante 使用的是 caffeine

## remote.${area}.type 详解

支持以下几种方式:

- `redis` (查看 `com.alicp.jetcache.autoconfigure.RedisAutoConfiguration` 配置类)，这个使用的是 jedis，注意要引入相应的依赖 `jetcache-starter-redis`
- `redis.lettuce` (查看 `com.alicp.jetcache.autoconfigure.RedisLettuceAutoConfiguration` 配置类)，这个使用的是 lettuce，注意引入相应的依赖 `jetcache-starter-redis-lettuce`
- `redisson` (查看 `com.alicp.jetcache.autoconfigure.RedissonAutoConfiguration` 配置类)，这个使用的是 redisson，注意引入相应的依赖 `jetcache-starter-redisson`
- `redis.springdata` (查看 `com.alicp.jetcache.autoconfigure.RedisSpringDataAutoConfiguration` 配置类)，这个使用的是 spring-data-redis，注意引入相应的依赖 `jetcache-starter-redis-springdata`

> dante 使用的是 redis.lettuce

## `remote.${area}.readFrom` 详解

`readFrom` 参数的补充说明：
- **master**: 从主节点读取
- **masterPreferred**: 优先从主节点读取，主服务不可用时再从从服务读取
- **replica**: 只从从服务读取数据
- **replicaPreferred**: 优先从从服务读取数据，没有可用从服务时再从主服务读取
- **lowestLatency**: 设置为在拓扑发现期间从延迟最低的节点读取。请注意，延迟测量是可以快速连续变化的瞬时快照。需要动态刷新源才能从群集中的所有节点获取拓扑和延迟。
- **any**: 可从任何节点读取
- **anyReplica**: 从任何从服务读取、

# 签章(stamp)的使用

`cn.herodotus.engine.cache.jetcache.stamp.StampManager` 接口用于定义在特定条件下生成后，在一定时间就会消除的标记性Stamp。
例如，幂等、短信验证码、Auth State等，用时生成，然后进行验证，之后再删除的标记Stamp。

## 计数器

应用场景一般是一段时间内的操作次数，例如登录失败次数、接口请求次数……
自定义一个计数器需要实现 `cn.herodotus.engine.cache.jetcache.stamp.AbstractCountStampManager` 并注册为 bean
如下所示：

```java
public class NumberCountStamp extends AbstractCountStampManager {
    public NumberCountStamp() {
        // 缓存的前缀
        super("sys:count:test");
    }

    @Override
    public Long nextStamp(String key) {
        return 1L;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 设置默认的超时时间
        super.setExpire(Duration.ofHours(1));
        // 默认最大计数次数
        super.setMaxTimes(10);
    }
}
```

然后注册为 bean

```java
@Configuration
public class StampConfiguration {

    @Bean
    public NumberCountStamp numberCountStamp(JetCacheCreateCacheFactory jetCacheCreateCacheFactory){
        // 加上 jetCacheCreateCacheFactory 是为了控制时序，否则启动的时候会报错
        return new NumberCountStamp();
    }
}
```

之后就可以在业务代码中使用了~

```java
@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {

    private final NumberCountStamp countStamp;

    @GetMapping("count")
    public Result<Integer> count()  {
        int counting = 0;
        Result<Integer> res = Result.empty();
        try {
            counting = countStamp.counting("kaiser");
        } catch (MaximumLimitExceededException e) {
            return Result.failure("超出计数器范围");
        }
        return res.status(200).data(counting);
    }
}
```

### 缓存签章

应用场景是某段时间后会自动失效的数据，例如验证码，也可以用做分布式锁
自定义签章需要实现 `cn.herodotus.engine.cache.jetcache.stamp.AbstractStampManager`，如下所示：

```java
public class CaptchaStamp extends AbstractStampManager<String, String> {
    public CaptchaStamp() {
        super("sys:captcha");
    }

    @Override
    public String nextStamp(String key) {
        // 这里可以搞一个生成验证码的方法
        return RandomUtil.randomNumbers(6);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 有效期5分钟
        super.setExpire(Duration.ofMinutes(5));
    }
}
```
然后注册为 bean

```java
@Configuration
public class StampConfiguration {

    @Bean
    public CaptchaStamp captchaStamp(JetCacheCreateCacheFactory jetCacheCreateCacheFactory){
        return new CaptchaStamp();
    }
}
```
之后就可以在业务代码中使用了~

```java
@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {

    private final CaptchaStamp captchaStamp;

    @GetMapping("captcha")
    public Result<String> captcha() {
        // 表时当前登录的用户
        String user = "kaiser";
        return Result.success("ok", captchaStamp.create(user));
    }

    @GetMapping("check")
    public Result<String> check(String key) {
        // 表时当前登录的用户
        String user = "kaiser";
        try {
            captchaStamp.check(user, key);
        } catch (Exception e) {
            return Result.failure("校验失败");
        }
        return Result.success("校验通过");
    }
}
```