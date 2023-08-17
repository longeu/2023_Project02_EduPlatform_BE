package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.entity.*;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.request.ActiveAccountRequest;
import com.kits_internship.edu_flatform.model.request.LoginRequest;
import com.kits_internship.edu_flatform.model.response.ActiveAccountResponse;
import com.kits_internship.edu_flatform.model.response.LoginResponse;
import com.kits_internship.edu_flatform.repository.OtpRepository;
import com.kits_internship.edu_flatform.repository.UserRepository;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.security.jwt.JwtService;
import com.kits_internship.edu_flatform.service.StudentService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

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
    private JwtService jwtService;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    DateConfig dateConfig;

    private static final String OTP = "123456";
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes

    @Override
    public UserEntity createAccount(UserEntity userEntity) {
        Map<String, Object> errors = new HashMap<>();
        UserEntity existUser = userRepository.findByEmailOrUsername(userEntity.getEmail(), userEntity.getUsername());
        if (existUser != null) {
            errors.put("user", "Email or Username existed!");
            throw new UnprocessableEntityException(errors);
        }
        userEntity.setStatus(StatusName.INACTIVE);
        userEntity.setCreatedDate(dateConfig.getTimestamp());
        userEntity.setModifiedDate(dateConfig.getTimestamp());
        UserEntity response = userRepository.save(userEntity);
        //OTP create
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setEmail(userEntity.getEmail());
        otpEntity.setOpt(OTP);
        otpEntity.setExpiredDate(new Timestamp(System.currentTimeMillis() + OTP_VALID_DURATION));
        otpEntity.setType(OtpType.ACTIVE_ACCOUNT);
        otpRepository.save(otpEntity);

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

            Optional<OtpEntity> optionalOtpEntity = otpRepository.findByEmail(activeAccountRequest.getEmail());
            OtpEntity otpEntity = optionalOtpEntity.orElseThrow();
            long currentTimeInMillis = System.currentTimeMillis();
            if (otpEntity.getExpiredDate().getTime() < currentTimeInMillis) {
                throw new UnprocessableEntityException("OTP has Expired!");
            }
            UserEntity userEntity = findByEmail(activeAccountRequest.getEmail());

            if (userEntity != null && userEntity.getStatus().equals(StatusName.INACTIVE) && activeAccountRequest.getOpt().equals(otpEntity.getOpt())) {
                userEntity.setPassword(encoder.encode(activeAccountRequest.getPassword()));
                userEntity.setStatus(StatusName.ACTIVE);
                if (userEntity.getRole().equals(RoleName.ROLE_TEACHER)) {
                    TeacherEntity teacherMapper = modelMapper.map(userEntity, TeacherEntity.class);
                    teacherMapper.setUser(userEntity);
                    TeacherEntity teacherEntity = teacherService.register(teacherMapper);
                    response = modelMapper.map(teacherEntity, ActiveAccountResponse.class);
                }
                if (userEntity.getRole().equals(RoleName.ROLE_STUDENT)) {
                    StudentEntity studentMapper = modelMapper.map(userEntity, StudentEntity.class);
                    studentMapper.setUser(userEntity);
                    StudentEntity studentEntity = studentService.register(studentMapper);
                    response = modelMapper.map(studentEntity, ActiveAccountResponse.class);
                }
                userEntity.setModifiedDate(dateConfig.getTimestamp());
                userRepository.save(userEntity);
                otpRepository.delete(otpEntity);

                response.setRole(userEntity.getRole());
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
        if (userEntity.get().getStatus().equals(StatusName.INACTIVE)) {
            errors.put("user", "Invalid user!");
            throw new UnprocessableEntityException(errors);
        }
        LoginResponse loginResponse = new LoginResponse();
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrinciple user = (UserPrinciple) authentication.getPrincipal();
            String jwt = jwtService.generateToken(user);
            loginResponse.setUsername(user.getUsername());
            loginResponse.setRole(userEntity.get().getRole());
            loginResponse.setToken(jwt);
        } catch (Exception e) {
            errors.put("user", "Username or Password invalid!");
            throw new UnprocessableEntityException(errors);
        }
        return loginResponse;
    }

    @Override
    public UserEntity findByUsername(String username) {

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.orElse(null);
    }

    @Override
    public ResponseEntity forgotPassword(String email) {
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setEmail(email);
        otpEntity.setOpt(OTP);
        otpEntity.setExpiredDate(new Timestamp(System.currentTimeMillis() + OTP_VALID_DURATION));
        otpEntity.setType(OtpType.RESET_PASSWORD);
        otpRepository.save(otpEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Success!");
    }

    @Override
    public ResponseEntity resetPassword(ActiveAccountRequest request) {
        Map<String, Object> errors = new HashMap<>();
        try {
            Optional<OtpEntity> optionalOtpEntity = otpRepository.findByEmail(request.getEmail());
            OtpEntity otpEntity = optionalOtpEntity.orElseThrow();
            long currentTimeInMillis = System.currentTimeMillis();
            if (otpEntity.getExpiredDate().getTime() < currentTimeInMillis) {
                throw new UnprocessableEntityException("OTP has Expired!");
            }
            UserEntity userEntity = findByEmail(request.getEmail());
            if (userEntity != null && request.getOpt().equals(otpEntity.getOpt())) {
                userEntity.setPassword(encoder.encode(request.getPassword()));
                userEntity.setModifiedDate(new Timestamp(currentTimeInMillis));
                userRepository.save(userEntity);
                otpRepository.delete(otpEntity);
                return ResponseEntity.status(HttpStatus.OK).body("Success!");
            } else {
                throw new NotFoundException("Invalid Request!");
            }
        } catch (Exception e) {
            errors.put("user", e.getMessage());
            throw new UnprocessableEntityException(errors);
        }
    }

}
