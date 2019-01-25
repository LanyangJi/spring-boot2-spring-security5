package com.jly.security.service;

import com.jly.security.bean.UserInfo;

/**
 * @author lanyangji
 * @date 2018/12/17 11:58
 */
public interface UserInfoService {

    /**
     * 按照姓名查询
     *
     * @param username
     * @return
     */
    UserInfo findByUsername(String username);

}
