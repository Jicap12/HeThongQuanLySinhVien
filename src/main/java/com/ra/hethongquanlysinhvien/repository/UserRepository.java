package com.ra.hethongquanlysinhvien.repository;

import com.ra.hethongquanlysinhvien.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserName(String userName);
}
