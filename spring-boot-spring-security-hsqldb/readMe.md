## 基于hsqldb（java内存数据库）的身份认证，角色授权

#### 步骤

- 新建实体类UserInfo保存用户信息（用户名和密码等）
```java
@Entity
@Data
public class UserInfo {

    @Id
    @GeneratedValue
    private Integer id;

    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum  Role {
        admin, normal
    }

}
```
- 新建UserRepository，继承 JpaRepository
```java
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo findByUserName(String userName);

}
```
- 新建UserInfoService
```java
public interface UserInfoService {
    
    UserInfo findByUserName(String userName);

}
// 实现类代码忽略
```
- 新建DataInit，保运两个用户数据进入内存数据库
```java
@Service
public class DataInit {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @PostConstruct
    public void init() {
        UserInfo admin = new UserInfo();
        admin.setUserName("admin");
        admin.setPassword("123");
        admin.setRole(UserInfo.Role.admin);
        userInfoRepository.save(admin);

        UserInfo user = new UserInfo();
        user.setUserName("user");
        user.setPassword("123");
        user.setRole(UserInfo.Role.normal);
        userInfoRepository.save(user);
    }

}
```
- 新建spring security的配置类WebSecurityConfig，继承WebSecurityConfigurerAdapter，注入密码加密工具
```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
```
- 自定义CustomUserDetailsService，实现UserDetailsService
```java
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
     * @throws 
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

        // 3. 封装用户的角色码
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 封装用户的角色码
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userInfo.getRole().name()));

        // 3. User封装用户的身份信息和角色码，必须加上 'Role_' 前缀
        User userDetails = new User(userInfo.getUserName(), passwordEncoder.encode(userInfo.getPassword()), authorities);

        return userDetails;
    }
}
```
- 新建MainController，进行测试
```java
@RestController
public class MainController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Security.";
    }

}
```
