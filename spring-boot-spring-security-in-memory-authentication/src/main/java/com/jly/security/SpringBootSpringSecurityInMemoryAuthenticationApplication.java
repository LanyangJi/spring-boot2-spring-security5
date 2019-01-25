package com.jly.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于内存验证
 *
 * @author lanyangji
 */
@SpringBootApplication
public class SpringBootSpringSecurityInMemoryAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSpringSecurityInMemoryAuthenticationApplication.class, args);
    }
}
