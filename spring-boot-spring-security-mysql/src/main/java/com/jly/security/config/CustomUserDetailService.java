package com.jly.security.config;

import com.jly.security.bean.Role;
import com.jly.security.bean.UserInfo;
import com.jly.security.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lanyangji
 * @date 2019/1/3 20:20
 */
@Component
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. 根据用户名从数据库中查出用户信息
        UserInfo dbUser = userInfoService.findByUsername(username);
        log.info("dbUser--->{}", dbUser);

        // 2. 如果查出为空，抛出没有发现用户异常
        if (dbUser == null) {
            throw new UsernameNotFoundException("没有此用户！");
        }

        // 3.2 封装用户的角色码
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色码必须以 'ROLE_' 开头
        for (Role role : dbUser.getRoles()) {
            log.info("当前用户拥有的权限--->{}", role.getRoleName());
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }

        // 3.1 User封装用户的信息
        User user = new User(dbUser.getUsername(), dbUser.getPassword(), authorities);

        return user;
    }
}
