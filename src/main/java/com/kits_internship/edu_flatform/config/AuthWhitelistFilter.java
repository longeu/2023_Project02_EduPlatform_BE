package com.kits_internship.edu_flatform.config;

import org.springframework.stereotype.Component;

@Component
public class AuthWhitelistFilter {
    public static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/api/user/register",
            "/api/user/login",
            "/api/user/activeAccount",
            "/api/user/forgotPassword",
            "/api/user/resetPassword"
    };
}
