package com.nguyenklinh.shopapp.repositorys;

import com.nguyenklinh.shopapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
