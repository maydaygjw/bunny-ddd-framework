package xyz.mayday.tools.bunny.ddd.context.autoconfigure;

import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import xyz.mayday.tools.bunny.ddd.schema.http.Response;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;

@RequiredArgsConstructor
@EnableSwagger2WebMvc
public class DocumentConfiguration {

  final OpenApiExtensionResolver openApiExtensionResolver;

  final TypeResolver typeResolver;

  @Bean(value = "defaultApi2")
  public Docket defaultApi2() {

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(
            new ApiInfoBuilder()
                .description("# BUNNY DDD Restful Api Document")
                .termsOfServiceUrl("http://www.xx.com/")
                .contact(new Contact("qq", "http://www.qq.com", "xx@qq.com"))
                .version("1.0")
                .build())
        // 分组名称
        .groupName("default")
        .select()
        .apis(RequestHandlerSelectors.basePackage("org.springframework").negate())
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")
        .genericModelSubstitutes(PageableData.class)
        .additionalModels(
            typeResolver.resolve(Response.class),
            typeResolver.resolve(PageableData.class),
            typeResolver.resolve(CommonQueryParam.class))
        .extensions(openApiExtensionResolver.buildExtensions("default"));
  }
}
