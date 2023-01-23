package com.learn.fallbackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FallbackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FallbackServiceApplication.class, args);
    }


}
