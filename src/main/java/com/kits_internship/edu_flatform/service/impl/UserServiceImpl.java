package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.entity.RoleName;
import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.request.ActiveAccountRequest;
import com.kits_internship.edu_flatform.model.request.LoginRequest;
import com.kits_internship.edu_flatform.model.response.ActiveAccountResponse;
import com.kits_internship.edu_flatform.model.response.LoginResponse;
import com.kits_internship.edu_flatform.repository.UserRepository;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.security.jwt.JwtProvider;
import com.kits_internship.edu_flatform.service.TeacherService;
import com.kits_internship.edu_flatform.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, UserRepository> implements UserService {
    public UserServiceImpl(UserRepository jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TeacherService teacherService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    private static final int OTP = 123456;
    @Override
    public UserEntity createAccount(UserEntity userEntity) {
        Map<String, Object> errors = new HashMap<>();
        UserEntity existUser = userRepository.findByEmailOrUsername(userEntity.getEmail(),userEntity.getUsername());
        if (existUser != null ) {
            errors.put("user", "Email or Username existed!");
            throw new UnprocessableEntityException(errors);
        }
        userEntity.setStatus(StatusName.INACTIVE);
        UserEntity response = create(userEntity);
        return response;
    }

    @Override
    public UserEntity findByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            return null;
        }
        return userEntity.get();
    }

    @Override
    @Transactional
    public ResponseEntity activeAccount(ActiveAccountRequest activeAccountRequest) {
        Map<String, Object> errors = new HashMap<>();

        try {
            ActiveAccountResponse response = new ActiveAccountResponse();
            UserEntity userEntity = findByEmail(activeAccountRequest.getEmail());
            if (userEntity != null && userEntity.getStatus().equals(StatusName.INACTIVE) && activeAccountRequest.getOpt() == OTP) {
                if (userEntity.getRole().equals(RoleName.ROLE_TEACHER)) {
                    userEntity.setPassword(encoder.encode(activeAccountRequest.getPassword()));
                    userEntity.setStatus(StatusName.ACTIVE);
                    update(userEntity.getId(), userEntity);

                    TeacherEntity teacherMapper = modelMapper.map(userEntity, TeacherEntity.class);
                    teacherMapper.setUser(userEntity);
                    TeacherEntity teacherEntity = teacherService.register(teacherMapper);

                    response = modelMapper.map(teacherEntity, ActiveAccountResponse.class);
                    response.setRole(RoleName.ROLE_TEACHER);
                }
                if (userEntity.getRole().equals(RoleName.ROLE_STUDENT)) {

                }
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                throw new NotFoundException("Invalid Request!");
            }
        } catch (Exception e) {
            errors.put("user", e.getMessage());
            throw new UnprocessableEntityException(errors);
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Map<String, Object> errors = new HashMap<>();

        Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity.isEmpty()) {
            errors.put("user", "Not found Username!");
            throw new NotFoundException(errors);
        }
        if(userEntity.get().getStatus().equals(StatusName.INACTIVE)){
            errors.put("user", "Invalid user!");
            throw new UnprocessableEntityException(errors);
        }
        LoginResponse loginResponse = new LoginResponse();
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrinciple user = (UserPrinciple) authentication.getPrincipal();
            String jwt = jwtProvider.generateToken(user);
            loginResponse.setEmail(userEntity.get().getEmail());
            loginResponse.setToken(jwt);
        } catch (Exception e) {
            errors.put("user", "Username or Password invalid!");
            throw new UnprocessableEntityException(errors);
        }
        return loginResponse;
    }
}
