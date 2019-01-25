package com.jly.security.bean;

import javax.persistence.*;

/**
 * @author lanyangji
 * @date 2019/1/4 21:46
 */
@Entity
@Table(name = "sys_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 描述
     */
    private String description;

    /**
     * mappedBy = "roles" 由另一方维护关联关系
     */
//    @JsonIgnoreProperties(value = "roles")
//    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
//    private List<UserInfo> userInfos;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
