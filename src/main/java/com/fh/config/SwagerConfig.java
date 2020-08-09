package com.fh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwagerConfig {

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo( ))
                .select( )
                .apis(RequestHandlerSelectors.basePackage("com.fh"))
                .paths(PathSelectors.any( ))
                .build( );
        return docket;
    }

    private ApiInfo apiInfo() {
        ApiInfo build = new ApiInfoBuilder( )
                .title("会员接口")
                .description("会员接口的描述")
                .contact(new Contact("艰苦奋斗", "", "260006856@qq.com"))
                .licenseUrl("http://www.baidu.com")
                .version("9.0")
                .license("The Apache License")
                .build( );
        return build;
    }
}
