package com.jly.security.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户实体类，主要用来存储用户名和密码
 *
 * @author lanyangji
 * @date 2018/12/11 20:58
 */
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
