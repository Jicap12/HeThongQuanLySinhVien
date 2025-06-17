package com.ra.hethongquanlysinhvien.controller;

import com.ra.hethongquanlysinhvien.model.entity.Course;
import com.ra.hethongquanlysinhvien.repository.CourseRepository;
import com.ra.hethongquanlysinhvien.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public String showCoursePage(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String sortBy,
            Model model) {

        Page<Course> coursePage = courseService.showListCourse(keyword, page, size, sortBy);

        model.addAttribute("coursePage", coursePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);;

        model.addAttribute("course", new Course());

        return "course";
    }

    @PostMapping("/add")
    public String addCourse(@ModelAttribute("course") @Valid Course course,
                            BindingResult result,
                            Model model,
                            @RequestParam(defaultValue = "") String keyword,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(defaultValue = "id") String sortBy) {

        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
                result.rejectValue("courseCode", "duplicate.courseCode", "Mã khóa học đã tồn tại");
            }
        if (courseRepository.existsByCourseName(course.getCourseName())) {
                result.rejectValue("courseName", "duplicate.courseName", "Tên khóa học đã tồn tại");
            }
        if (result.hasErrors()) {
            // Giữ lại dữ liệu phân trang
            model.addAttribute("coursePage", courseService.showListCourse(keyword, page, size, sortBy));
            model.addAttribute("openAddModal", true); // Flag để mở modal
            return "course";
        }

        courseRepository.save(course);
        return "redirect:/course";
    }

    @PostMapping("/edit")
    public String update(
            @ModelAttribute("course") @Valid Course course,
            BindingResult result,
            @RequestParam("id") Long id,
            Model model,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Course existingCourse = courseRepository.findById(id).orElse(null);

        if (existingCourse == null) {
            return "redirect:/course";
        }

        // Kiểm tra trùng mã (chỉ khi mã thay đổi)
        if (!existingCourse.getCourseCode().equals(course.getCourseCode())) {
            if (courseRepository.existsByCourseCode(course.getCourseCode())) {
                result.rejectValue("courseCode", "duplicate.courseCode", "Mã khóa học đã tồn tại");
            }
        }

        // Kiểm tra trùng tên (chỉ khi tên thay đổi)
        if (!existingCourse.getCourseName().equals(course.getCourseName())) {
            if (courseRepository.existsByCourseName(course.getCourseName())) {
                result.rejectValue("courseName", "duplicate.courseName", "Tên khóa học đã tồn tại");
            }
        }

        if (result.hasErrors()) {
            // Giữ lại dữ liệu phân trang
            model.addAttribute("coursePage", courseService.showListCourse(keyword, page, size, sortBy));
            model.addAttribute("openEditModal", true); // Flag để mở modal
            return "course";
        }

        // Cập nhật lại thông tin mới
        existingCourse.setCourseCode(course.getCourseCode());
        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setDurationHours(course.getDurationHours());
        existingCourse.setStatus(course.getStatus());

        courseRepository.save(existingCourse);
        return "redirect:/course";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        courseService.deleteCourseById(id);
        return "redirect:/course";
    }
}
