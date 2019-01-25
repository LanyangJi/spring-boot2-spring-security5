## 基于内存的身份认证，角色授权

#### 步骤

- 新建配置类，继承 WebSecurityConfigurerAdapter，并重新configure方法
- 标注 @EnableWebSecurity, 启用spring security
- 注入密码加密工具 BCryptPasswordEncoder
- 重写configure方法，添加用户和密码，以及角色
- 标注 @EnableGlobalMethodSecurity(prePostEnabled = true) 开启方法级别的安全控制，括号里的配置会拦截标注了@PreAuthorize注解的配置
- 在需要授权相应角色的方法面前加上 @PreAuthorize("hasAnyRole('admin')")

```java
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
```

```java
package com.jly.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @EnableWebSecurity 启用 spring security
 * @EnableGlobalMethodSecurity(prePostEnabled = true) 开启方法级别的安全控制，
 *                                                 括号里的配置会拦截标注了@PreAuthorize注解的配置
 *
 *  方法级别角色授权
 *       1.  给角色添加角色码 roles("admin")
 *       2. 开启注解 @EnableGlobalMethodSecurity(prePostEnabled = true)
 *       3. 在需要授权相应角色的方法面前加上 @PreAuthorize("hasAnyRole('admin')")
 *
 * @author lanyangji
 * @date 2018/12/11 19:11
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 需要手动注入密码加密器，比如 BCryptPasswordEncoder
     *
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 通过重写 configure 方法，进行创建用户
     * <p>
     * .passwordEncoder(new BCryptPasswordEncoder()) 指定加密工具
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
         * 基于内存的方式，构建两个用户 admin:123456, user:123456
         *
         * 方式一
         *       auth.inMemoryAuthentication()
         *           .passwordEncoder(new BCryptPasswordEncoder())
         *           .withUser("admin")
         *           .password(new BCryptPasswordEncoder().encode("123456"))
         *           .roles();
         *
         *  方式二
         *      如下
         *       @Autowired
         *        private PasswordEncoder passwordEncoder;
         */
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder.encode("123456"))
                .roles("admin");
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder.encode("123456"))
                .roles("normal");
    }

    /**
     * 注入密码加密工具 BCryptPasswordEncoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

```