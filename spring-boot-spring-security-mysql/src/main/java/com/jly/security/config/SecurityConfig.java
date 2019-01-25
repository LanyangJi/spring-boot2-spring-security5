package com.jly.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lanyangji
 * @date 2018/12/17 12:09
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 拦截标注了 @preAuthorize()注解的方法，没标注的所有用户可以访问
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 指定登录页
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login") // 自定义登录页
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll() // (配置白名单)允许所有人访问登录页，这个必须在拦截所有之前
                .antMatchers("/index", "/").permitAll() // (配置白名单)首页所有人可以访问
                .antMatchers("/test/**", "/test2/**").permitAll() // 指定路径下的不用登陆就可以访问
                .antMatchers("/res/*.{js,css}").permitAll() // res目录下的所有js和css文件不用登陆就可以访问
                .anyRequest().access("@authService.canAccess(request,authentication)") // 自定义URL授权，则要去掉下面的所有认证，这里要注意
                //.anyRequest().authenticated() // 所有的请求都要在登录之后才可以访问
                .and()
                .sessionManagement()
                .maximumSessions(1) // session并发控制，同一个账号只能在一个地点登录（一个浏览器）
                ;

        // 注册自定义过滤器
//        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(new AtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(new AfterLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 密码加密工具
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
