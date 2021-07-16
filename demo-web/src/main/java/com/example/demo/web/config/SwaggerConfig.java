//package com.example.demo.web.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.ArrayList;
//
//@Configuration
//public class SwaggerConfig {
//
//    // todo:接口文档
//
//    //配置了Swagger的Docket的bean实例
//    //enable是否启动swagger，如果为False则Swagger不能在浏览器访问
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                //RequestHandlerSelectors,配置要扫描接口的方式
//                //basePackage:指定要扫描的包
//                //any()：扫描全部
//                //none():不扫描
//                //.withClassAnnotation():扫描类上的注解
//                //.withMethodAnnotation():扫描方法上的注解
//                .apis(RequestHandlerSelectors.basePackage("com.example.demo.web.api"))
//                //paths()过滤什么路径
//                /*.paths(PathSelectors.ant("/mi/**"))*/
//                .build();
//    }
//
//    //作者信息
//    Contact contact = new Contact("小唐", "xiaotangstudio.cn", "1738743304@qq.com");
//
//    //配置Swagger 信息 = ApiInfo
//    private ApiInfo apiInfo() {
//        return new ApiInfo("小唐的Api文档",
//                "好好看文档",
//                "1.0",
//                "xiaotangstudio.cn",
//                contact,
//                "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0",
//                new ArrayList<>());
//    }
//
//}