package com.kits_internship.edu_flatform.config;

import org.springframework.stereotype.Component;

@Component
public class AuthListFilter {
    public static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/api/user/register",
            "/api/user/login",
            "/api/user/activeAccount",
            "/api/user/forgotPassword",
            "/api/user/resetPassword",
            "/api/category/list",
            "/api/course/list",
            "/api/user/resentOTP"
    };

    public static final String[] TEACHER_LIST = {
            "/api/teacher/**",
            "/api/course/**"
    };

    public static final String[] STUDENT_LIST = {
            "/api/student/**",
    };

    public static final String[] ADMIN_LIST = {
            "/api/category/**",
    };
}
