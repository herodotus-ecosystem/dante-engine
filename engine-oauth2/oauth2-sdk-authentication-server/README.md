## OAuth 2.0 自定义模式模块

**包含以下内容：**
1. Spring HerodotusAuthorization Server 工具类。
2. 自定义 OAuth 2.0 密码模式。

### 额外说明

1. Spring HerodotusAuthorization Server 工具类。新版 spring-security-oauth2-authorization-server 很多代码都是“包”级可访问的，外部无法使用。为了方便扩展将其提取出来，便于使用。代码内容与原包代码基本一致。
2. 最新的 OAuth 2 标准，已经不再包含密码模式。但是由于早期密码模式使用特别广泛，特别是基于 Vue 的前端。短期内无法将该模式剔除，因此予以保留。