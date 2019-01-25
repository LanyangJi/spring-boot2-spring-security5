package com.jly.security.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

/**
 * @author lanyangji
 * @date 2019/1/5 21:05
 */
public interface PermissionService {

    Map<String, Collection<ConfigAttribute>> getPermissionMap();

}
