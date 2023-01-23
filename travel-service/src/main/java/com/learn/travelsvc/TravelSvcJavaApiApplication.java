package com.learn.travelsvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


import java.time.LocalDateTime;


@Log4j2
@EnableDiscoveryClient
@SpringBootApplication
public class TravelSvcJavaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelSvcJavaApiApplication.class, args);
        log.info("TravelsJavaAPI started successfully at {}", LocalDateTime.now());
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${api.key}") String appDesciption, @Value("${api.version}}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Travel API")
                        .version(appVersion)
                        .description(appDesciption)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
