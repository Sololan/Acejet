package com.wejoy.wejoycms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "com.wejoy")
public class WejoyCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WejoyCmsApplication.class, args);
    }
}
