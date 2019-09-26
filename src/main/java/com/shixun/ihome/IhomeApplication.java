package com.shixun.ihome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
@MapperScan({"com.shixun.ihome.test.mapper","com.shixun.ihome.publicservice.mapper"})
public class IhomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(IhomeApplication.class, args);
    }

}
