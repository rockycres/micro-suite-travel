package com.learn.ordersvc.common.util.config;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Class that implements the necessary settings for using Swagger as an API documentation tool.
 *  
 */
@Configuration
@Profile({"dev"})
public class OpenApiConfiguration {
	
	@Value("${release.version}")
	private String releaseVersion;
	
	@Value("${api.version}")
	private String apiVersion;



	@Bean
	public GroupedOpenApi api() {
		return GroupedOpenApi.builder()
				.addOpenApiCustomiser(a -> a.info(info()))
				.group("orderapi-public")
				.pathsToMatch("/com/learn/ordersvc/controller/**")
				.build();
	}

	public Info info() {
		return new Info()
				.title("Travel API")
				.description("API for creating orders")
				.version(releaseVersion.concat("_").concat(apiVersion))
				.contact(apiContact())
				.license(apiLicence());
	}


	private License apiLicence() {
		return new License()
				.name("MIT Licence")
				.url("https://opensource.org/licenses/mit-license.php");
	}

	private Contact apiContact() {
		return new Contact()
				.name("xx")
				.email("xxxxx.xx@xx.com")
				.url("https://github.com/xx");
	}

}




