package com.ra.hethongquanlysinhvien.controller;

import com.ra.hethongquanlysinhvien.enums.StudentStatus;
import com.ra.hethongquanlysinhvien.model.entity.StudentEntity;
import com.ra.hethongquanlysinhvien.model.entity.User;
import com.ra.hethongquanlysinhvien.service.ClassService;
import com.ra.hethongquanlysinhvien.service.StudentService;
import com.ra.hethongquanlysinhvien.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassService classService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String showStudentPage(@RequestParam(name = "keyword", required = false) String keyword,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "5") int size,
                                  @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                  Model model) {
        Page<StudentEntity> studentPage = studentService.showListStudent(keyword, page, size, sortBy);
        model.addAttribute("studentPage", studentPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);

        model.addAttribute("studentEntity", new StudentEntity());
        model.addAttribute("studentStatuses", StudentStatus.values());
        model.addAttribute("classes", classService.findAllClass());
        return "student";
    }

    @GetMapping("/available")
    public String getAvailableUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Page<User> userPage = userService.findAllAvailableUsers(keyword, page, size, sortBy, sortDir);

        model.addAttribute("userPage", userPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("studentEntity", new StudentEntity());
        model.addAttribute("studentStatuses", StudentStatus.values());
        model.addAttribute("classes", classService.findAllClass());

        return "availableUsers"; // Thymeleaf template
    }

    @PostMapping("/add")
    public String addStudent(
            @ModelAttribute("studentEntity") @Valid StudentEntity studentEntity,
            BindingResult result,
            @RequestParam(name = "userId") Long userId,
            Model model,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy
    ) {
        // Kiểm tra trùng mã trước khi validate các trường khác
        if (studentService.existsByStudentCode(studentEntity.getStudentCode())) {
            result.rejectValue("studentCode", "error.studentEntity", "Mã sinh viên đã tồn tại");
        }

        if (result.hasErrors()) {
            // Load lại danh sách user available để hiển thị trong modal
            Page<User> userPage = userService.findAllAvailableUsers(keyword, page, size, "userName", "asc");

            model.addAttribute("userPage", userPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", userPage.getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("sortDir", "asc");
            model.addAttribute("reverseSortDir", "desc");

            // Thêm dữ liệu cần thiết cho form
            model.addAttribute("studentStatuses", StudentStatus.values());
            model.addAttribute("classes", classService.findAllClass());
            model.addAttribute("studentEntity", studentEntity); // Giữ lại dữ liệu đã nhập
            model.addAttribute("openAddStudentModal", true); // Mở modal add student

            return "availableUsers";
        }

        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        // Gán User vào StudentEntity
        studentEntity.setUser(user);

        studentService.saveAddStudent(studentEntity);

        return "redirect:/student";
    }

    @PostMapping("/edit")
    public String editStudent(@ModelAttribute("studentEntity") @Valid StudentEntity studentEntity,
                              BindingResult result,
                              @RequestParam("id") Long id,
                              Model model,
                              @RequestParam(name = "keyword", required = false) String keyword,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size,
                              @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {

        StudentEntity newStudent = studentService.findStudentById(id);

        try {
            // Set lại ID để Service biết đây là update, không phải insert
            studentEntity.setId(id);

            // Gọi Service để vừa validate vừa lưu luôn
            studentService.saveUpdateStudent(studentEntity);

        } catch (IllegalArgumentException ex) {
            // Gắn lỗi vào field cụ thể (ở đây là studentCode)
            result.rejectValue("studentCode", "error.studentEntity", ex.getMessage());
        }
        if (result.hasErrors()) {
            Page<StudentEntity> studentPage = studentService.showListStudent(keyword, page, size, sortBy);
            model.addAttribute("studentPage", studentPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", studentPage.getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("sortBy", sortBy);

            model.addAttribute("studentStatuses", StudentStatus.values());
            model.addAttribute("classes", classService.findAllClass());
            model.addAttribute("studentEntity", studentEntity);

            model.addAttribute("openEditStudentModal", true);

            return "student";
        }

        newStudent.setStudentCode(studentEntity.getStudentCode());
        newStudent.setStudentName(studentEntity.getStudentName());
        newStudent.setDateOfBirth(studentEntity.getDateOfBirth());
        newStudent.setGender(studentEntity.getGender());
        newStudent.setAddress(studentEntity.getAddress());
        newStudent.setPhone(studentEntity.getPhone());
        newStudent.setStatus(studentEntity.getStatus());
        newStudent.setClassEntity(studentEntity.getClassEntity());

        studentService.saveUpdateStudent(newStudent);

        return "redirect:/student";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/student";
    }
}