package com.yuqiong.college.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/1
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(webApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("网站-课程中心API文档")
                .description("本文档描述了课程中心微服务接口定义")
                .version("1.0")
                .contact(new Contact("yuqiong", "http://yuqiong.com", "1446647113@qq.com"))
                .build();
    }


}