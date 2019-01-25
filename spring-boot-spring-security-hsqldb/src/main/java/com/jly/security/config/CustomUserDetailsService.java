package com.jly.security.config;

import com.jly.security.domain.UserInfo;
import com.jly.security.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义UserDetailsService，类似于 shiro中的 realm，发挥“数据源”的作用，去获取用户认证信息
 * @author lanyangji
 * @date 2018/12/11 22:19
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 1. 通过 username 获取到 userInfo 信息
     * 2. 通过 user(UserDeatail) 返回 UserDetail
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailsService.loadUserByUsername");

        // 1. 根据用户名查询用户信息
        UserInfo userInfo = userInfoService.findByUserName(s);

        // 2. 如果用户不存在，在抛出没有此用户异常
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new UsernameNotFoundException("没有此用户！");
        }

        // 3.2 封装用户的角色码
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 封装用户的角色码，必须加上 'Role_' 前缀
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userInfo.getRole().name()));

        // 3.1 User封装用户的身份信息和角色码
        User userDetails = new User(userInfo.getUserName(), passwordEncoder.encode(userInfo.getPassword()), authorities);

        return userDetails;
    }
}
