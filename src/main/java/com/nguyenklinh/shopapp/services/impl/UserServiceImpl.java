package com.nguyenklinh.shopapp.services.impl;

import com.nguyenklinh.shopapp.components.JwtTokenUtil;
import com.nguyenklinh.shopapp.dtos.ChangePasswordDTO;
import com.nguyenklinh.shopapp.dtos.UpdateUserDTO;
import com.nguyenklinh.shopapp.dtos.UserDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.DataNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    public String login(String phoneNumber, String password)  {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()){
            throw new MyException(ErrorCode.CAN_NOT_FIND_USER);
        }
        User existingUser = optionalUser.get();
        if (existingUser.getFacebookAccountId() == 0
                && existingUser.getGoogleAccountId() == 0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new MyException(ErrorCode.USERNAME_OR_PASSWORD_INVALID);
            }
        }
        if (!existingUser.isActive()){
            throw new MyException(ErrorCode.USER_ID_LOCKED);
        }
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(phoneNumber,password,existingUser.getAuthorities());

        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public User getUserDetailsFromToken(String authorizationHeader) {
        String extractedToken = authorizationHeader.substring(7);
        if (jwtTokenUtil.isTokenExpired(extractedToken)){
            throw new MyException(ErrorCode.TOKEN_EXPIRED);
        }
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(extractedToken);
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()){
            throw new MyException(ErrorCode.CAN_NOT_FIND_USER);
        }
        return optionalUser.get();
    }

    @Override
    @Transactional
    public User updateUser(Long id, UpdateUserDTO updateUserDTO) {
        User existingUser  = userRepository.findById(id)
                .orElseThrow(() -> new MyException(ErrorCode.CAN_NOT_FIND_USER,id));

        if (!existingUser.getPhoneNumber().equals(updateUserDTO.getPhoneNumber()))
            if(userRepository.existsByPhoneNumber(updateUserDTO.getPhoneNumber())){
                throw new MyException(ErrorCode.PHONE_NUMBER_ALREADY_EXIST);
        }

        userMapper.updateUser(existingUser,updateUserDTO);
        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void changePassword(Long id, ChangePasswordDTO changePasswordDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new MyException(ErrorCode.CAN_NOT_FIND_USER,id));
        if(!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), existingUser.getPassword())) {
            throw new MyException(ErrorCode.CURRENT_PASSWORD_INVALID);
        }
        if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())){
            throw new MyException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        String newPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        existingUser.setPassword(newPassword);
        userRepository.save(existingUser);
    }
}
