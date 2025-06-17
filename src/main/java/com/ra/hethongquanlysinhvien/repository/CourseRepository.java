package com.ra.hethongquanlysinhvien.repository;

import com.ra.hethongquanlysinhvien.model.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    long countCourseByStatus(boolean status);

    Page<Course> findByCourseNameContainingIgnoreCase(String keyword, Pageable pageable);

    boolean existsByCourseCode(String courseCode);
    boolean existsByCourseName(String courseName);

}