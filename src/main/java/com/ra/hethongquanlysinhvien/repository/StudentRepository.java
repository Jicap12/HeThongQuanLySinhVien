package com.ra.hethongquanlysinhvien.repository;

import com.ra.hethongquanlysinhvien.enums.StudentStatus;
import com.ra.hethongquanlysinhvien.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<StudentEntity, Long>, JpaSpecificationExecutor<StudentEntity> {
    long countStudentByStatus(StudentStatus studentStatus);

    boolean existsByStudentCode(String studentCode);
    boolean existsByEmail(String email);
}
