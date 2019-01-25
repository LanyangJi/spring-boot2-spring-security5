package com.jly.security.service.impl;

import com.jly.security.bean.Permission;
import com.jly.security.bean.Role;
import com.jly.security.repository.PermissionRepository;
import com.jly.security.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 1. 在程序启动的时候加载权限信息，这里可以使用 @PostConstruct
 * 2. 如何存储这些权限信息？
 * @author lanyangji
 * @date 2019/1/5 21:06
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * key=url, value=角色码集合
     */
    private Map<String, Collection<ConfigAttribute>> permissionMap = null;

    @PostConstruct
    public void initPermissions() {
        /*
         * 从数据库中获取到所有的权限信息，然后遍历，存储到permissionMap中
         */
        permissionMap = new HashMap<>();
        List<Permission> permissions = permissionRepository.findAll();
        for (Permission permission : permissions) {
            Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
            for (Role role : permission.getRoles()) {
                ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + role.getRoleName());
                configAttributes.add(configAttribute);
            }
            permissionMap.put(permission.getUrl(), configAttributes);
        }
        log.info("permissionMap --> {}", permissionMap);
    }

    @Override
    public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        // 如果是空的，则重新初始化
        if (CollectionUtils.isEmpty(permissionMap)) {
            initPermissions();
        }
        return permissionMap;
    }
}
