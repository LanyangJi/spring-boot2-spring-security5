package com.jly.security;

import com.jly.security.domain.UserInfo;
import com.jly.security.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootSpringSecurityHsqldbApplicationTests {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void contextLoads() {

        UserInfo userInfo = new UserInfo();
        userInfo.setId(0);
        userInfo.setUserName("吴婷");
        userInfo.setPassword("123");

        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoList.add(userInfo);

        userInfoRepository.saveAll(userInfoList);

        List<UserInfo> all = userInfoRepository.findAll();
        all.forEach(System.out::println);
    }

}
