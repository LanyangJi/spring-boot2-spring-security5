package com.jly.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 如果要关闭验证，添加如下配置
 *
 * @author lanyangji
 * @SpringBootApplication(exclude = SecurityAutoConfiguration.class)
 */
@SpringBootApplication
public class SpringBootSpringSecurityHelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSpringSecurityHelloWorldApplication.class, args);
    }
}
