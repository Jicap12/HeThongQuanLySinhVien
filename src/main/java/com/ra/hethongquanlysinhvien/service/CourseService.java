package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.model.entity.Course;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    long getTotalCourses();
    long getActiveCourses();
    long getEndedCourses();

    Page<Course> showListCourse(String keyword, int page, int size, String sortBy);

    Course saveOrUpdateCourse(Course course);

    void deleteCourseById(Long id);

    public List<Course> findAllCourse();

}
