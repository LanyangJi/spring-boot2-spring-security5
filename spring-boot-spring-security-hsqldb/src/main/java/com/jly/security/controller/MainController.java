package com.jly.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lanyangji
 * @date 2018/12/11 22:43
 */
@RestController
public class MainController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Security.";
    }

}
