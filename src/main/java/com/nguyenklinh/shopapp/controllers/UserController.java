package com.nguyenklinh.shopapp.controllers;

import com.nguyenklinh.shopapp.components.MessageUtil;
import com.nguyenklinh.shopapp.dtos.ChangePasswordDTO;
import com.nguyenklinh.shopapp.dtos.UpdateUserDTO;
import com.nguyenklinh.shopapp.dtos.UserDTO;
import com.nguyenklinh.shopapp.dtos.UserLoginDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.models.User;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.responses.UserResponse;
import com.nguyenklinh.shopapp.services.UserService;
import com.nguyenklinh.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;
    private final MessageUtil messageUtil;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO){
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())){
            throw new MyException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message(messageUtil.getMessage(MessageKeys.SUCCESS_REGISTER_USER))
                .result(user)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        String token = userService.login(userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword());

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message(messageUtil.getMessage(MessageKeys.SUCCESS_LOGIN_USER))
                .result(token)
                .build());
    }
    @PostMapping("/details")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String authorizationHeader){
        User user = userService.getUserDetailsFromToken(authorizationHeader);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(UserResponse.fromUser(user))
                .build());
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
                                        @Valid @RequestBody UpdateUserDTO updateUserDTO,
                                        @RequestHeader("Authorization") String authorizationHeader){
        User user = userService.getUserDetailsFromToken(authorizationHeader);
        if (!user.getId().equals(id)){
            throw new MyException(ErrorCode.ACCESS_DENIED);
        }
        User userUpdate = userService.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message(messageUtil.getMessage(MessageKeys.SUCCESS_UPDATE_USER))
                .result(UserResponse.fromUser(userUpdate))
                .build());
    }
    @PutMapping("/{id}/change-password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(@PathVariable("id") Long id,
                                            @RequestBody @Valid ChangePasswordDTO changePasswordDTO,
                                            @RequestHeader("Authorization") String authorizationHeader){
        User user = userService.getUserDetailsFromToken(authorizationHeader);
        if(!user.getId().equals(id)){
            throw new MyException(ErrorCode.ACCESS_DENIED);
        }
        userService.changePassword(id,changePasswordDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message(messageUtil.getMessage(MessageKeys.SUCCESS_CHANGE_PASSWORD))
                .build());
    }
}
