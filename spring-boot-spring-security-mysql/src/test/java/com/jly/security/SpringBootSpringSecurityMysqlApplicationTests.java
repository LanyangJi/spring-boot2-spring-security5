package com.jly.security;

import com.jly.security.bean.Permission;
import com.jly.security.bean.Role;
import com.jly.security.bean.UserInfo;
import com.jly.security.repository.PermissionRepository;
import com.jly.security.repository.RoleRepository;
import com.jly.security.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootSpringSecurityMysqlApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    public void testFind() {
        UserInfo userInfo = userInfoRepository.findByUsername("wuting");
        System.out.println(userInfo);
        System.out.println(userInfo.getRoles());
    }

    @Test
    public void test() {

        // 用户
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("wuting");
        userInfo.setPassword(passwordEncoder.encode("123"));

        UserInfo userInfo2 = new UserInfo();
        userInfo2.setUsername("jly");
        userInfo2.setPassword(passwordEncoder.encode("123"));

        UserInfo userInfo3 = new UserInfo();
        userInfo3.setUsername("panzixuan");
        userInfo3.setPassword(passwordEncoder.encode("123"));

        // 角色
        Role role = new Role();
        role.setRoleName("admin");
        role.setDescription("管理员");

        Role role2 = new Role();
        role2.setRoleName("user");
        role2.setDescription("普通用户");

        // 权限
        Permission permission = new Permission();
        permission.setName("普通用户的url");
        permission.setDescription("允许普通用户和管理员访问的url");
        permission.setUrl("/helloUser");
        permission.setRoles(Lists.newArrayList(role2, role));

        Permission permission2 = new Permission();
        permission2.setName("管理员的url");
        permission2.setDescription("只允许管理员访问的url");
        permission2.setUrl("/helloAdmin");
        permission2.setRoles(Lists.newArrayList(role));


        // 设置用户和角色关系
        userInfo.setRoles(Lists.newArrayList(role));;
        userInfo2.setRoles(Lists.newArrayList(role2));;
        userInfo3.setRoles(Lists.newArrayList(role2));;


        // 保存
        roleRepository.saveAll(Lists.newArrayList(role, role2));
        userInfoRepository.saveAll(Lists.newArrayList(userInfo, userInfo2, userInfo3));
        permissionRepository.saveAll(Lists.newArrayList(permission, permission2));
    }
}

