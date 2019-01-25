package com.jly.security.config;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author lanyangji
 * @date 2019/1/5 16:00
 */
public class AtLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("This is a filter at UsernamePasswordAuthenticationFilter.");
        chain.doFilter(req, res);
    }
}
