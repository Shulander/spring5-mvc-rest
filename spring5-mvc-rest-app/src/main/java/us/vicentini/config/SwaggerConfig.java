package us.vicentini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .pathMapping("/")
                .tags(new Tag("customer-controller", "This is my Customer API"),
                      new Tag("category-controller", "This is my Category API"),
                      new Tag("vendor-controller", "This is my Vendor API"))
                .apiInfo(metaData());
    }


    private ApiInfo metaData() {
        return new ApiInfo(
                "Spring5 mvc Rest",
                "Spring Framework 5 MVC Rest Application",
                "1.0",
                "Terms of Service: This comes with ABSOLUTELY NO WARRANTY, to the extent permitted by applicable law.",
                new Contact("Henrique Vicentini", "https://github.com/Shulander", "shulander@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
