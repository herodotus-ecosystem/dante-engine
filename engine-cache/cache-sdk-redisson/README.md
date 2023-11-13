# Redisson相关代码组件模块

# Redisson 功能

## 1. 添加依赖

```xml
<dependency>
  <groupId>cn.herodotus.engine</groupId>
  <artifactId>cache-sdk-redisson</artifactId>
</dependency>
```

## 2. 配置说明

```yaml
spring:
  data:
    redis:
      # 单机模式
      host: 127.0.0.1
      port: 6795
      database: 0
      password:
      # 哨兵模式
#      sentinel:
#        master: mymaster
#        nodes: 192.168.8.129:27001,192.168.8.129:27002,192.168.8.129:27003
      # 集群模式
#      cluster:
#        nodes: 192.168.8.129:8001,192.168.8.129:8002,192.168.8.129:8003,192.168.8.129:8004,192.168.8.129:8005,192.168.8.129:8006
    redisson:
      # 是否启用
      enabled: false
      # Redis连接模式
      mode: SINGLE | SENTINEL | CLUSTER
      # 配置文件
      config:
```
## 3. 常用方法可以参考[这篇文章](https://zhuanlan.zhihu.com/p/596334390?utm_id=0)
