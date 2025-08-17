package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.enums.StudentStatus;
import com.ra.hethongquanlysinhvien.model.entity.StudentEntity;
import com.ra.hethongquanlysinhvien.model.entity.User;
import com.ra.hethongquanlysinhvien.repository.StudentRepository;
import com.ra.hethongquanlysinhvien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

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
            return studentRepository.findAll((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("studentName")), "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("studentCode")), "%" + keyword.toLowerCase() + "%")
            ), pageable);
        }
        return studentRepository.findAll(pageable);
    }

    @Override
    public void saveAddStudent(StudentEntity studentEntity) {

        User user = userRepository.findById(studentEntity.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        // Gán User vào Student
        studentEntity.setUser(user);

        studentRepository.save(studentEntity);
    }

    @Override
    public void saveUpdateStudent(StudentEntity updateStudentEntity) {
        // Lấy thông tin StudentEntity hiện tại từ cơ sở dữ liệu
        StudentEntity studentEntity = studentRepository.findById(updateStudentEntity.getId())
                .orElseThrow(() -> new IllegalArgumentException("Sinh viên không tồn tại"));

        // Nếu mã mới khác mã cũ thì mới kiểm tra trùng
        if (!updateStudentEntity.getStudentCode().equals(studentEntity.getStudentCode())) {
            if (studentRepository.existsByStudentCode(updateStudentEntity.getStudentCode())) {
                throw new IllegalArgumentException("Mã sinh viên đã tồn tại");
            }
        }
        // Cập nhật thông tin StudentEntity
        studentEntity.setStudentCode(studentEntity.getStudentCode());
        studentEntity.setStudentName(studentEntity.getStudentName());
        studentEntity.setDateOfBirth(studentEntity.getDateOfBirth());
        studentEntity.setGender(studentEntity.getGender());
        studentEntity.setAddress(studentEntity.getAddress());
        studentEntity.setPhone(studentEntity.getPhone());
        studentEntity.setStatus(studentEntity.getStatus());
        studentEntity.setClassEntity(studentEntity.getClassEntity());

        studentRepository.save(studentEntity);
    }


    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public StudentEntity findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Học viên không tồn tại với id: " + id));
    }

    @Override
    public boolean existsByStudentCode(String studentCode) {
        return studentRepository.existsByStudentCode(studentCode);
    }


    @Override
    public List<StudentEntity> findAllStudent() {
        return studentRepository.findAll();
    }
}