package com.ra.hethongquanlysinhvien.repository;

import com.ra.hethongquanlysinhvien.enums.ClassStatus;
import com.ra.hethongquanlysinhvien.model.entity.ClassEntity;
import com.ra.hethongquanlysinhvien.model.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassRepository extends JpaRepository<ClassEntity, Long>, JpaSpecificationExecutor<ClassEntity> {
    long countClassByStatus(ClassStatus status);
//    Page<ClassEntity> findByClassNameContainingIgnoreCase(String keyword, Pageable pageable);

    boolean existsByClassCode(String classCode);
    boolean existsByClassName(String className);
}
