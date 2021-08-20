package com.tang.enums.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Predicate;


/**
 * @author Hero
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig implements WebMvcConfigurer {



    @Value("${spring.application.name}")
    private String title;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决 SWAGGER 404报错
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/");
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description("enums demo")
                .contact(new Contact("T_T", "https://swagger.io/", "tangjian@yzw.cn"))
                .version("1.0").build();
    }

    @Bean
    @Primary
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tang.enums.demo.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(Timestamp.class, Long.class);
    }




    @Bean
    SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId("test-app-client-id")
                .clientSecret("test-app-client-secret")
                .realm("test-app-realm")
                .appName("test-app")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                // 底部 models 默认展开深度 0不展开, 默认展开太长了
                .defaultModelsExpandDepth(0)
                // 接口文档中实体的默认展开深度
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                // 显示请求的响应时间
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.NONE)
                // tag过滤
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null)
                .build();
    }


}
