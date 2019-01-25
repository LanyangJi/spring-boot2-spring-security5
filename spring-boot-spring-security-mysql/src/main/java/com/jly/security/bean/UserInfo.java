package com.jly.security.bean;

import javax.persistence.*;
import java.util.List;

/**
 * @author lanyangji
 * @date 2018/12/17 10:55
 */
@Table(name = "sys_user_info")
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    /**
     * 多对多
     * FetchType.EAGER 立即加载
     * mappedBy = "roles" 由另一方维护关联关系
     * @JsonIgnoreProperties(value = {"userInfos"}) 防止json序列化时，由于双向关联，陷入死循环
     */
    // @JsonIgnoreProperties(value = {"userInfos"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
