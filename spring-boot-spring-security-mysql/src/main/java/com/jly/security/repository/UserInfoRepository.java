package com.jly.security.repository;

import com.jly.security.bean.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lanyangji
 * @date 2018/12/17 11:01
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor<UserInfo> {

    /**
     * 按照姓名查询
     *
     * @param username
     * @return
     */
    UserInfo findByUsername(String username);
}
