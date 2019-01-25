package com.jly.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于hsqldb内存数据库的身份验证，角色授权
 *
 * @author lanyangji
 */
@SpringBootApplication
public class SpringBootSpringSecurityHsqldbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSpringSecurityHsqldbApplication.class, args);
    }
}
