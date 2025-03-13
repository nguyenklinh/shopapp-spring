package com.nguyenklinh.shopapp.services;

import com.nguyenklinh.shopapp.dtos.ChangePasswordDTO;
import com.nguyenklinh.shopapp.dtos.UpdateUserDTO;
import com.nguyenklinh.shopapp.dtos.UserDTO;
import com.nguyenklinh.shopapp.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User createUser(UserDTO userDTO) ;
    String login(String phoneNumber, String password) ;

    User getUserDetailsFromToken(String token);
    User updateUser(Long id, UpdateUserDTO updateUserDTO);
    void changePassword(Long id, ChangePasswordDTO changePasswordDTO);
}
