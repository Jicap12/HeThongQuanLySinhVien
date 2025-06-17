package com.ra.hethongquanlysinhvien.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã khóa học không được để trống")
    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @NotBlank(message = "Tên khóa học không được để trống")
    @Column(name = "course_name", nullable = false, unique = true)
    private String courseName;

    @NotNull(message = "Thời lượng không được để trống")
    @Min(value = 1, message = "Thời lượng phải lớn hơn 0")
    @Column(name = "duration_hours")
    private Integer durationHours;

    @Column(name = "status")
    private Boolean status;
}
