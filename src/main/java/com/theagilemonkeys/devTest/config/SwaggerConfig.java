package com.theagilemonkeys.devTest.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private static final String BASIC_AUTH = "basicAuth";
	private static final String BEARER_AUTH = "Bearer";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.theagilemonkeys.devTest")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
//		.securitySchemes(securitySchemes())
//				.securityContexts(List.of(securityContext()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Monkeys REST API", "API Test to perform CRUD opertations", "1.0", "Terms of service",
				new Contact("Cristian López", "www.google.com", "cristianlopgon@gmail.com"), "License of API",
				"API license URL", Collections.emptyList());
	}

	private List<SecurityScheme> securitySchemes() {
		return List.of(new BasicAuth(BASIC_AUTH), new ApiKey(BEARER_AUTH, "Authorization", "header"));
	}

	private SecurityContext securityContext() {
		// List.of(basicAuthReference(), bearerAuthReference())
		return SecurityContext.builder().securityReferences(Arrays.asList(basicAuthReference(), bearerAuthReference()))
				.forPaths(PathSelectors.ant("/products/**")).build();
	}

	private SecurityReference basicAuthReference() {
		return new SecurityReference(BASIC_AUTH, new AuthorizationScope[0]);
	}

	private SecurityReference bearerAuthReference() {
		return new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]);
	}
}