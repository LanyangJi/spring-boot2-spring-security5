## 注意
1. 在WebSecurityConfigurerAdapter的子类上标注了@EnableGlobalMethodSecurity(prePostEnabled = true) ，就会拦截标注了 @preAuthorize()注解的方法，没标注的所有用户可以访问。

2. 可以通过重写configure(HttpSecurity http)方法，指定自己的登录页面

3. 通过在目录下新建 error/403.html，是spring security指定的默认的无权限页面

```java
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
        http.formLogin().loginPage("/login");
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

```