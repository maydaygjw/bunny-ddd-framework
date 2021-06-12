package xyz.mayday.tools.bunny.ddd.sample.config;

import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import xyz.mayday.tools.bunny.ddd.schema.http.Response;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;

@Configuration
@EnableSwagger2WebMvc
@RequiredArgsConstructor
public class DocumentConfiguration {

    final OpenApiExtensionResolver openApiExtensionResolver;

    final TypeResolver typeResolver;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //.title("swagger-bootstrap-ui-demo RESTful APIs")
                        .description("# swagger-bootstrap-ui-demo RESTful APIs")
                        .termsOfServiceUrl("http://www.xx.com/")
                        .contact("xx@qq.com")
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("1.2.x")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("xyz.mayday.tools.bunny.ddd.sample.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(PageableData.class)
//                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PageableData.class), typeResolver.resolve(WildcardType.class)))
                .additionalModels(typeResolver.resolve(Response.class), typeResolver.resolve(PageableData.class))
                .extensions(openApiExtensionResolver.buildExtensions("1.2.x"));
    }
}
