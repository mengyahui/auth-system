package com.auth.system;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author MYH
 * @time 2023/04/03 下午 08:34
 */
@SpringBootApplication
@MapperScan("com.auth.system.mapper")
@ComponentScan("com.auth")
public class AuthSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthSystemApplication.class,args);
    }
}