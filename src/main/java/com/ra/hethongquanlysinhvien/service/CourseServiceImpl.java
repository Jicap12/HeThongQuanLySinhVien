package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.model.entity.Course;
import com.ra.hethongquanlysinhvien.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public long getTotalCourses() {
        return courseRepository.count();
    }
    @Override
    public long getActiveCourses() {
        return courseRepository.countCourseByStatus(true);
    }
    @Override
    public long getEndedCourses() {
        return courseRepository.countCourseByStatus(false);
    }

    @Override
    public Page<Course> showListCourse(String keyword, int page, int size, String sortBy) {
        Pageable pageable;

        if (sortBy == null || sortBy.isBlank()) {
            pageable = PageRequest.of(page, size); // Không sắp xếp nếu không chọn sort
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        if (keyword == null || keyword.isBlank()) {
            return courseRepository.findAll(pageable);
        } else {
            return courseRepository.findByCourseNameContainingIgnoreCase(keyword, pageable);
        }
    }

    @Override
    public Course saveOrUpdateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }
}
