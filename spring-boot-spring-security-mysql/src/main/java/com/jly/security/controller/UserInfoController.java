package com.jly.security.controller;

import com.jly.security.bean.UserInfo;
import com.jly.security.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.plugin.liveconnect.SecurityContextHelper;

/**
 * @author lanyangji
 * @date 2018/12/17 12:01
 */
@Slf4j
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/user")
    @ResponseBody
    public UserInfo userInfo(String username) {
        log.info("---->{}", userInfoService.findByUsername(username));
        return userInfoService.findByUsername(username);
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello, Spring Security.";
    }

    /**
     * @PreAuthorize("hasAnyRole('admin')") 在自定义URL拦截的时候，要去掉。否则一直报出没有权限
     * @return
     */
    @GetMapping("/adminPage")
    @ResponseBody
    //@PreAuthorize("hasAnyRole('admin')")
    public String adminPage() {
        return "Hello, adminPage";
    }
    @GetMapping("/userPage")
    @ResponseBody
    //@PreAuthorize("hasAnyRole('user')")
    public String userPage() {
        return "Hello, userPage";
    }

    @GetMapping("/helloUser")
    @ResponseBody
    public String helloUser() {
        return "Admin and user can access the page.";
    }

    @ResponseBody
    @GetMapping("/helloAdmin")
    public String helloAdmin() {
        return "Only admin can access the page";
    }

    @GetMapping({"/index", "/", ""})
    public String index(Model model) {
        // 获取认证登录的对象
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 未登录的情况下将返回一个字符串 'anonymousUser'
        if ("anonymousUser".equals(principal)) {
            model.addAttribute("name", "anonymous user");
        } else {
            // 登录的情况下，将返回的是在 CustomeUserDetailService的 loadUserByUsername的方法中返回的 User对象
            User user = (User) principal;
            model.addAttribute("name", ((User) principal).getUsername());
        }

        return "index";
    }


}
