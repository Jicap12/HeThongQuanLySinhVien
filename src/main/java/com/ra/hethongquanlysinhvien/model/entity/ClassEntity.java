package com.ra.hethongquanlysinhvien.model.entity;

import com.ra.hethongquanlysinhvien.enums.ClassStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "class")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng giá trị ID
    private Long id;

    @NotBlank(message = "Mã lớp học không được để trống")
    @Column(name = "class_code", nullable = false, unique = true)
    private String classCode;

    @NotBlank(message = "Tên lớp học không được để trống")
    @Column(name = "class_name", nullable = false, unique = true)
    private String className;

    @NotBlank(message = "Tên giáo viên không được để trống")
    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "description")
    private String description;

    @Column(name = "number_of_students")
    @Min(value = 1, message = "Thời lượng phải lớn hơn 0")
    private Integer numberOfStudents;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    // Trạng thái lớp học (đang hoạt động, chờ lớp, kết thúc), kiểu enum lưu dạng chuỗi
    private ClassStatus status;

    @ManyToOne // Quan hệ nhiều lớp thuộc về 1 khóa học
    @JoinColumn(name = "course_id", nullable = false)
    // Khóa ngoại liên kết đến bảng "courses", không được null
    private Course course;
}
