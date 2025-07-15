package com.ra.hethongquanlysinhvien.controller;

import com.ra.hethongquanlysinhvien.service.CourseService;
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



    //chỗ này truy vấn sql 3 lần ko tối ưu, cần cập nhật dùng @Query để trả về cả ba thống kê cùng lúc qua dto nhanh hơn
    @GetMapping
    public String dashboard(Model model) {
        long countCourse = courseService.getTotalCourses();
        long countActiveCourse = courseService.getActiveCourses();
        long countEndedCourse = courseService.getEndedCourses();
        model.addAttribute("countCourse", countCourse);
        model.addAttribute("courseActive", countActiveCourse);
        model.addAttribute("courseEnded", countEndedCourse);
        return "dashboard";
    }
}