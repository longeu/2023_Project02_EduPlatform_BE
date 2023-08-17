package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.security.UserPrinciple;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.Optional;

public class BaseController {
    protected Optional<UserPrinciple> getJwtUser(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)principal;
            if (authentication.getPrincipal() instanceof UserPrinciple) {
                UserPrinciple user = (UserPrinciple)authentication.getPrincipal();
                return Optional.ofNullable(user);
            }
        }

        return Optional.empty();
    }

}