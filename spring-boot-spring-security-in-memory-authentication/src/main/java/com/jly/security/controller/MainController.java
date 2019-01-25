package com.jly.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lanyangji
 * @date 2018/12/11 16:57
 */
@RestController
@RequestMapping("/hello")
public class MainController {

    @GetMapping
    public String index() {
        return "Hello, Spring Security.";
    }

    @GetMapping("/helloAdmin")
    @PreAuthorize("hasAnyRole('admin')")
    public String helloAdmin() {
        return "Hello, admin";
    }

    @GetMapping("/helloUser")
    @PreAuthorize("hasAnyRole('normal')")
    public String helloUser() {
        return "Hello, user";
    }

}
