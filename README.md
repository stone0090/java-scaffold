# 工程简介

## 技术选型

- Java脚手架：[Aliyun Java Initializr](https://start.aliyun.com/bootstrap.html)
- 核心框架：[Spring Boot](https://spring.io/projects/spring-boot)
- 数据库层：[MyBatis 3](https://mybatis.org/mybatis-3/zh/index.html)
- 数据库连接池：[Druid](https://github.com/alibaba/druid)
- 数据库：[h2database](http://www.h2database.com/html/main.html)
- 缓存：[Ehcache](https://www.ehcache.org/)
- 安全框架：[Apache Shiro](http://shiro.apache.org/) 
- 诊断工具：[Arthas](https://arthas.aliyun.com/doc/)
- 前端框架：[Ant Design Pro](https://pro.ant.design)
- 接口文档：[swagger](https://swagger.io/)

## 功能设计（初步设想）
- 部门管理
- 用户管理
- 角色管理
- 菜单管理
- 权限分配
- 参数管理
- 数据字典
- 定时任务
- 业务日志
- 登录日志

---

## Jackson
### Jackson简介
这个工程基于常见的MVC架构，展示了Jackson（the Java JSON library）的使用。学习这个工程之前，需要同学们对MVC有一定的了解。

### Jackson工程解读

本项目的工程结构如下。

视图的不同可以通过“手动写逻辑”的方式来实现，但是在Spring Boot中，我们仅仅需要定义视图类、数据标注、API标注就更简便地达到相同的效果。

```
.
├── README.md
├── only3.json
├── pom.xml
└── src
    └── main
        ├── java
        │   └── demo
        │       └── jacksondemo
        │           ├── Application.java//SpringBoot入口，main函数
        │           ├── DO//数据对象。数据对象的视图在每个对象中定义。
        │           │   ├── Message.java//数据结构标注
        │           │   └── User.java//数据结构标注
        │           ├── controller//Controller，接收前端的映射
        │           │   └── MessageController.java//API标注
        │           ├── service//接口与实现分离的服务
        │           │   ├── MessageService.java
        │           │   └── impl
        │           │       └── MessageServiceImpl.java
        │           └── view//视图类的定义
        │               └── View.java//视图类
        └── resources
            └── application.properties//各种配置选项

11 directories, 15 files
```


# 延伸阅读

### Jackson延伸阅读

* Jackson Homepage： https://github.com/FasterXML/jackson

* Jackson教程：https://www.baeldung.com/category/json/jackson/

* Java工程脚手架： https://start.aliyun.com
