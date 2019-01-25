package com.jly.security.repository;

import com.jly.security.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lanyangji
 * @date 2019/1/4 21:51
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
