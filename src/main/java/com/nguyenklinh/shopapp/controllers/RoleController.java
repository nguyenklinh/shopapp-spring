package com.nguyenklinh.shopapp.controllers;

import com.nguyenklinh.shopapp.models.Role;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping
    public ResponseEntity<?> getAllRole(){
        List<Role> roles = roleService.getAllRole();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(roles)
                .build());
    }
}
