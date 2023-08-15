package com.kits_internship.edu_flatform.security;

import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User not found " + email);
        }
        Set<String> roles = new HashSet<>();
        roles.add(String.valueOf(userEntity.get().getRole()));

        return UserPrinciple.build(userEntity.get(), roles);
    }
}