# 工程简介

## 技术选型
- 脚手架：[Aliyun Java Initializr](https://start.aliyun.com/bootstrap.html)
- 核心框架：[Spring Boot](https://spring.io/projects/spring-boot)
- 数据库层：[MyBatis 3](https://mybatis.org/mybatis-3/zh/index.html)
- 数据库：[h2database](http://www.h2database.com/html/main.html)
- 连接池：[Druid](https://github.com/alibaba/druid)
- 安全框架：[Apache Shiro](http://shiro.apache.org/)
- 前端框架：[Ant Design Pro](https://pro.ant.design)
- 接口文档：[swagger](https://swagger.io/)
- 诊断工具：[Arthas](https://arthas.aliyun.com/doc/)

## 访问入口
- 系统首页：
  - 访问入口：http://localhost:8000
  - 账号密码：stone / 123456
- h2数据库：
  - 访问入口：http://127.0.0.1:8080/h2-console
  - 账号密码：san /（没有密码）
  - jdbcurl：jdbc:h2:file:./demo-dao/src/main/resources/database/demo
- druid监控：
  - 访问入口：http://localhost:8080/druid/index.html
  - 账号密码：stone / 123456
  
## 功能设计（初步设想）
- 用户管理
- 角色管理
- 权限管理

## 日志规范