package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.exception.UnauthorizedException;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BaseController {

    protected final Map<String, Object> errors = new HashMap<>();

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

    protected void adminCheck(Optional<UserPrinciple> currentUser){
        String role = currentUser.get().getAuthorities().stream().findAny().get().getAuthority();
        if(!role.equals("ROLE_ADMIN")){
            throw new UnauthorizedException();
        }
    }
}
