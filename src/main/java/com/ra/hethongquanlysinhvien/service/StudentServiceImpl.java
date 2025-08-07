package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.enums.StudentStatus;
import com.ra.hethongquanlysinhvien.model.entity.StudentEntity;
import com.ra.hethongquanlysinhvien.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public long getTotalStudents() {
        return studentRepository.count();
    }

    @Override
    public long getTotalStudentsWaitingForClass() {
        return studentRepository.countStudentByStatus(StudentStatus.WAITING_FOR_CLASS);
    }

    @Override
    public long getTotalStudentsStudying() {
        return studentRepository.countStudentByStatus(StudentStatus.STUDYING);
    }

    @Override
    public long getTotalStudentsDeferred() {
        return studentRepository.countStudentByStatus(StudentStatus.DEFERRED);
    }

    @Override
    public long getTotalStudentsSuspended() {
        return studentRepository.countStudentByStatus(StudentStatus.SUSPENDED);
    }

    @Override
    public long getTotalStudentsGraduated() {
        return studentRepository.countStudentByStatus(StudentStatus.GRADUATED);
    }

    @Override
    public Page<StudentEntity> showListStudent(String keyword, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        if (keyword != null && !keyword.trim().isEmpty()) {
            return studentRepository.findAll(((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("studentName")), "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("studentCode")), "%" + keyword.toLowerCase() + "%")
            )), pageable);
        }
        return studentRepository.findAll(pageable);
    }

    @Override
    public StudentEntity saveOrUpdateStudent(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentEntity> findAllStudent() {
        return studentRepository.findAll();
    }
}
