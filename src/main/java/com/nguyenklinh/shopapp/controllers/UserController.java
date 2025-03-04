package com.nguyenklinh.shopapp.controllers;

import com.nguyenklinh.shopapp.dtos.UserDTO;
import com.nguyenklinh.shopapp.dtos.UserLoginDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.models.User;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.responses.UserResponse;
import com.nguyenklinh.shopapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO){
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())){
            throw new MyException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result("successfully")
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        String token = userService.login(userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword());

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(token)
                .build());
    }
    @PostMapping("/details")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token){
        User user = userService.getUserDetailsFromToken(token);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(UserResponse.fromUser(user))
                .build());
    }
}
