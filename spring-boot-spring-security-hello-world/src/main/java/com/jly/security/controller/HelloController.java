package com.jly.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lanyangji
 * @date 2018/12/11 13:34
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Security.";
    }

    @GetMapping("/git")
    @ResponseBody
    public String print() {
        return "Hello, git.";
    }
}
