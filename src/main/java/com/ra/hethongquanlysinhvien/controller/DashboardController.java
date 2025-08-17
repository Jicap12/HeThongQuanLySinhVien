package com.ra.hethongquanlysinhvien.controller;

import com.ra.hethongquanlysinhvien.service.ClassService;
import com.ra.hethongquanlysinhvien.service.CourseService;
import com.ra.hethongquanlysinhvien.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ClassService classService;
    @Autowired
    private StudentService studentService;

    //chỗ này truy vấn sql 3 lần ko tối ưu, cần cập nhật dùng @Query để trả về cả ba thống kê cùng lúc qua dto nhanh hơn
    @GetMapping
    public String dashboard(Model model) {
        long countCourse = courseService.getTotalCourses();
        long countActiveCourse = courseService.getActiveCourses();
        long countEndedCourse = courseService.getEndedCourses();
        model.addAttribute("countCourse", countCourse);
        model.addAttribute("courseActive", countActiveCourse);
        model.addAttribute("courseEnded", countEndedCourse);
        long countClass = classService.getTotalClasses();
        long countActiveClass = classService.getTotalActiveClasses();
        long countCompletedClass = classService.getTotalCompletedClasses();
        long countWaitingClass = classService.getTotalWaitingClasses();
        model.addAttribute("countClass", countClass);
        model.addAttribute("classActive", countActiveClass);
        model.addAttribute("classCompleted", countCompletedClass);
        model.addAttribute("classWaiting", countWaitingClass);
        long countStudent = studentService.getTotalStudents();
        long countStudentsWaitingForClass = studentService.getTotalStudentsWaitingForClass();
        long countStudentsStudying = studentService.getTotalStudentsStudying();
        long countStudentsDeferred = studentService.getTotalStudentsDeferred();
        long countStudentsSuspended = studentService.getTotalStudentsSuspended();
        long countStudentsGraduated = studentService.getTotalStudentsGraduated();
        model.addAttribute("countStudent", countStudent);
        model.addAttribute("countStudentsWaitingForClass", countStudentsWaitingForClass);
        model.addAttribute("countStudentsStudying", countStudentsStudying);
        model.addAttribute("countStudentsDeferred", countStudentsDeferred);
        model.addAttribute("countStudentsSuspended", countStudentsSuspended);
        model.addAttribute("countStudentsGraduated", countStudentsGraduated);

        return "dashboard";
    }
}