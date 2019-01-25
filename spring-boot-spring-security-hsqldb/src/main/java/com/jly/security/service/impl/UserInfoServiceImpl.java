package com.jly.security.service.impl;

import com.jly.security.domain.UserInfo;
import com.jly.security.repository.UserInfoRepository;
import com.jly.security.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lanyangji
 * @date 2018/12/11 22:11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByUserName(String userName) {
        return userInfoRepository.findByUserName(userName);
    }
}
