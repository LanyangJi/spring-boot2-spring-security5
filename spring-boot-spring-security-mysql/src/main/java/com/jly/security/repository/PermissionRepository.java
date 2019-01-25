package com.jly.security.repository;

import com.jly.security.bean.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lanyangji
 * @date 2019/1/5 20:57
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
