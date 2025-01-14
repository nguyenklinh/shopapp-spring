package com.nguyenklinh.shopapp.services.impl;

import com.nguyenklinh.shopapp.components.JwtTokenUtil;
import com.nguyenklinh.shopapp.dtos.UserDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.mapper.UserMapper;
import com.nguyenklinh.shopapp.models.Role;
import com.nguyenklinh.shopapp.models.User;
import com.nguyenklinh.shopapp.repositorys.RoleRepository;
import com.nguyenklinh.shopapp.repositorys.UserRepository;
import com.nguyenklinh.shopapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) {
        String phoneNumber = userDTO.getPhoneNumber();
        // Kiểm tra xem số điện thoại đã tồn tại hay chưa
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new MyException(ErrorCode.USER_EXISTED);
        }
        userDTO.setRoleId(1L);
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(()-> new MyException(ErrorCode.CAN_NOT_FIND_ROLE));

        //convert from userDTO => user
        User user = userMapper.toUser(userDTO);
        user.setActive(true);
        user.setRole(role);
        // Kiểm tra nếu có accountId, không yêu cầu password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()){
            throw new MyException(ErrorCode.USERNAME_OR_PASSWORD_INVALID);
        }
        User existingUser = optionalUser.get();
        if (existingUser.getFacebookAccountId() == 0
                && existingUser.getGoogleAccountId() == 0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new MyException(ErrorCode.USERNAME_OR_PASSWORD_INVALID);
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(phoneNumber,password,existingUser.getAuthorities());

        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
