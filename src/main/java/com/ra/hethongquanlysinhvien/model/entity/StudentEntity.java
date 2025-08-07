package com.ra.hethongquanlysinhvien.model.entity;

import com.ra.hethongquanlysinhvien.enums.StudentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Column(name = "student_code", nullable = false, unique = true)
    private String studentCode;

    @NotBlank(message = "Họ và tên không được để trống")
    @Column(name = "full_name", nullable = false)
    private String studentName;

    // Ngày sinh dùng kiểu LocalDate để dễ xử lý logic
    @NotNull(message = "Ngày sinh không được để trống")
    @Column(name = "date_of_birth", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private Boolean gender;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(name = "address", nullable = false)
    private String address;

    // Email: duy nhất, không rỗng, có kiểm tra định dạng
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "status", nullable = false)
    private StudentStatus status;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity;
}

