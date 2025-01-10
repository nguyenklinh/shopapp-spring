package com.nguyenklinh.shopapp.services;

import com.nguyenklinh.shopapp.dtos.UserDTO;
import com.nguyenklinh.shopapp.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User createUser(UserDTO userDTO) ;
    String login(String phoneNumber, String password) ;
}
