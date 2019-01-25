package com.jly.security.service;

import com.jly.security.domain.UserInfo;

/**
 * @author lanyangji
 * @date 2018/12/11 22:09
 */
public interface UserInfoService {

    /**
     * 根据用户名查询
     *
     * @param userName
     * @return
     */
    UserInfo findByUserName(String userName);

}
