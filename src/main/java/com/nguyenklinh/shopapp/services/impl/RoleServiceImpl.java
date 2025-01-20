package com.nguyenklinh.shopapp.services.impl;

import com.nguyenklinh.shopapp.models.Role;
import com.nguyenklinh.shopapp.repositorys.RoleRepository;
import com.nguyenklinh.shopapp.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRole() {
        List<Role> roles = roleRepository.findAll();
        return roles;
    }
}
