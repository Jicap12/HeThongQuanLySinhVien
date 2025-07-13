package com.ra.hethongquanlysinhvien.repository;

import com.ra.hethongquanlysinhvien.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String name);
}
