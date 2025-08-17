package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.model.entity.StudentEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    long getTotalStudents();
    long getTotalStudentsWaitingForClass();
    long getTotalStudentsStudying();
    long getTotalStudentsDeferred();
    long getTotalStudentsSuspended();
    long getTotalStudentsGraduated();

    Page<StudentEntity> showListStudent(String keyword, int page, int size, String sortBy);

    void saveAddStudent(StudentEntity studentEntity);

    void saveUpdateStudent(StudentEntity studentEntity);

    void deleteStudentById(Long id);

    StudentEntity findStudentById(Long id);

    boolean existsByStudentCode(String studentCode);

    List<StudentEntity> findAllStudent();
}
