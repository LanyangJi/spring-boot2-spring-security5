package com.jly.security.repository;

import com.jly.security.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lanyangji
 * @date 2018/12/11 22:06
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    /**
     * 根据用户名查询
     *
     * @param userName
     * @return
     */
    UserInfo findByUserName(String userName);

}
