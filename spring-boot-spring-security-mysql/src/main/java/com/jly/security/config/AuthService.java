package com.jly.security.config;

import com.jly.security.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

/**
 * 自定义 动态URL授权
 *
 * @author lanyangji
 * @date 2019/1/5 21:32
 */
@Service
@Slf4j
public class AuthService {

    /**
     * 未登录的话，spring security 默认用户名是 'anonymousUser'
     */
    private static final String UN_AUTH = "anonymousUser";

    @Autowired
    private PermissionService permissionService;

    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
        boolean b = false;

        /**
         * 1. 未登录的情况下，需要做一个判断或者拦截.
         *      anonymousUser || userDetailService返回的 User对象
         */
        Object principal = authentication.getPrincipal();
        if (principal == null || UN_AUTH.equals(principal)) {
            return b;
        }

        /**
         * 2. 匿名的角色 ROLE_ANONYMOUS
         */
        if (authentication instanceof AnonymousAuthenticationToken) {
            // 匿名角色
            // check
            // return;
        }

        /**
         * 3. 通过 request对象url, 获取到对应的权限信息
         */
        Map<String, Collection<ConfigAttribute>> permissionMap = permissionService.getPermissionMap();
        // 匹配
        // 这里不能使用如下方式，因为有局限，只能识别 /hello/helloUser等类型，无法识别 /hello/** 类别
        // Collection<ConfigAttribute> collection = permissionMap.get(request.getRequestURI());

        //AntPathRequestMatcher
        // 封装当前请求链接的权限信息
        Collection<ConfigAttribute> configAttributes = null;
        for (String currentUrl : permissionMap.keySet()) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(currentUrl);
            if (matcher.matches(request)) {
                configAttributes = permissionMap.get(currentUrl);
                break;
            }
        }

        // 如果没有匹配到
        if (CollectionUtils.isEmpty(configAttributes)) {
            return b;
        }

        /**
         * 4. 将获取到的权限信息和当前登录的账号的权限信息进行比对
         */
        for (ConfigAttribute configAttribute : configAttributes) {
            // ROLE_admin || ROLE_user
            String role = configAttribute.getAttribute();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (role.equals(authority.getAuthority())) {
                    log.info("match successfully --> {}", role);
                    b = true;
                    break;
                }
            }
        }

        return b;
    }

}
