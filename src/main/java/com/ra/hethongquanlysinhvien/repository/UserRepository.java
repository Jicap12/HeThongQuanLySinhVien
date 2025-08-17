package com.ra.hethongquanlysinhvien.repository;

import com.ra.hethongquanlysinhvien.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    // Trường hợp không tìm kiếm, chỉ lọc các user ROLE_USER và chưa có student
    @Query("""
        SELECT u
        FROM User u
        JOIN u.roles r
        WHERE r.roleName = 'ROLE_USER'
          AND SIZE(u.roles) = 1
          AND u.student IS NULL
    """)
    Page<User> findAllAvailableUsers(Pageable pageable);

    // Trường hợp tìm kiếm theo keyword (email hoặc userName)
    @Query("""
        SELECT u
        FROM User u
        JOIN u.roles r
        WHERE r.roleName = 'ROLE_USER'
          AND SIZE(u.roles) = 1
          AND u.student IS NULL
          AND (
              LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
    """)
    Page<User> searchAllAvailableUsers(@Param("keyword") String keyword, Pageable pageable);
}