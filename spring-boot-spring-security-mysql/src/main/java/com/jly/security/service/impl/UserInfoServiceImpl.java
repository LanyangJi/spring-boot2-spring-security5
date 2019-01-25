package com.jly.security.service.impl;

import com.jly.security.bean.UserInfo;
import com.jly.security.repository.UserInfoRepository;
import com.jly.security.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author lanyangji
 * @date 2018/12/17 11:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        return userInfoRepository.findByUsername(username);
    }
}
