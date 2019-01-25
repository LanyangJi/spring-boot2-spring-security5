package com.jly.security.init;

import com.jly.security.domain.UserInfo;
import com.jly.security.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 将数据存储内存数据库
 *
 * @author lanyangji
 * @date 2018/12/11 22:37
 */
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
