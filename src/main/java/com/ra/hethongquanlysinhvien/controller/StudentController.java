package com.ra.hethongquanlysinhvien.controller;

import com.ra.hethongquanlysinhvien.enums.ClassStatus;
import com.ra.hethongquanlysinhvien.enums.StudentStatus;
import com.ra.hethongquanlysinhvien.model.entity.ClassEntity;
import com.ra.hethongquanlysinhvien.model.entity.StudentEntity;
import com.ra.hethongquanlysinhvien.repository.ClassRepository;
import com.ra.hethongquanlysinhvien.repository.StudentRepository;
import com.ra.hethongquanlysinhvien.service.ClassService;
import com.ra.hethongquanlysinhvien.service.StudentService;
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
    private StudentRepository studentRepository;
    @Autowired
    private ClassService classService;

    @GetMapping
    public String showStudentPage(@RequestParam(name = "keyword", required = false) String keyword, // Từ khóa tìm kiếm (nếu có)
                                  @RequestParam(name = "page", defaultValue = "0") int page, // Trang hiện tại
                                  @RequestParam(name = "size", defaultValue = "5") int size, // Số dòng mỗi trang
                                  @RequestParam(name = "sortBy", defaultValue = "id") String sortBy, // Sắp xếp theo trường nào
                                  Model model) {

        // Gọi service để lấy danh sách lớp theo phân trang, tìm kiếm
        Page<StudentEntity> studentPage = studentService.showListStudent(keyword, page, size, sortBy);

        // Đưa dữ liệu ra view
        model.addAttribute("studentPage", studentPage); // Toàn bộ thông tin phân trang
        model.addAttribute("currentPage", page); // Trang hiện tại
        model.addAttribute("totalPages", studentPage.getTotalPages()); // Tổng số trang
        model.addAttribute("keyword", keyword); // Từ khóa tìm kiếm hiện tại
        model.addAttribute("sortBy", sortBy); // Trường sắp xếp
        model.addAttribute("studentEntity", new StudentEntity());
        model.addAttribute("studentStatuses", StudentStatus.values()); // Danh sách trạng thái lớp học
        model.addAttribute("classes", classService.findAllClass()); // Danh sách tất cả các khóa học trong DB

        return "student";
    }
    @PostMapping("/add")
    public String addStudent(
            @ModelAttribute("studentEntity") @Valid StudentEntity studentEntity, // Lấy dữ liệu từ form và kiểm tra validation
            BindingResult result, // Kết quả kiểm tra hợp lệ
            Model model, // Model để truyền dữ liệu ra view
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy
    ) {

        // Kiểm tra trùng mã sinh viên
        if (studentRepository.existsByStudentCode(studentEntity.getStudentCode())) {
            result.rejectValue("studentCode", "duplicate.studentCode", "Mã sinh viên đã tồn tại");
        }

        // Kiểm tra trùng email (nếu cần)
        if (studentRepository.existsByEmail(studentEntity.getEmail())) {
            result.rejectValue("email", "duplicate.email", "Email đã tồn tại");
        }

        if (result.hasErrors()) {
            // Load lại danh sách lớp (phân trang) để hiển thị trong view
            Page<StudentEntity> studentPage = studentService.showListStudent(keyword, page, size, sortBy);
            model.addAttribute("studentPage", studentPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", studentPage.getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("studentStatuses", StudentStatus.values());
            model.addAttribute("classes", classService.findAllClass());
            model.addAttribute("studentEntity", studentEntity);
            model.addAttribute("openAddStudentModal", true);
            return "student";
        }
        // Nếu không có lỗi, lưu sinh viên mới vào DB
        studentService.saveOrUpdateStudent(studentEntity);
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
        StudentEntity existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent == null) {
            return "redirect:/class";
        }

        // Kiểm tra trùng mã (chỉ khi mã thay đổi)
        if (!existingStudent.getStudentCode().equals(studentEntity.getStudentCode())) {
            if (studentRepository.existsByStudentCode(studentEntity.getStudentCode())) {
                result.rejectValue("studentCode", "duplicate.studentCode", "Mã sinh viên đã tồn tại");
            }
        }

        // Kiểm tra trùng tên (chỉ khi tên thay đổi)
        if (!existingStudent.getEmail().equals(studentEntity.getEmail())) {
            if (studentRepository.existsByEmail(studentEntity.getEmail())) {
                result.rejectValue("email", "duplicate.email", "Email đã tồn tại");
            }
        }

        if (result.hasErrors()) {
            // Giữ lại dữ liệu phân trang
            model.addAttribute("studentPage", studentService.showListStudent(keyword, page, size, sortBy));
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", studentService.showListStudent(keyword, page, size, sortBy).getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("studentStatuses", StudentStatus.values());
            model.addAttribute("classes", classService.findAllClass()); // Thêm danh sách khóa học
            model.addAttribute("studentEntity", studentEntity); // Giữ dữ liệu đã nhập
            model.addAttribute("openEditStudentModal", true);
            return "student";
        }

        // Cập nhật lại thông tin mới
        existingStudent.setStudentCode(studentEntity.getStudentCode());
        existingStudent.setStudentName(studentEntity.getStudentName());
        existingStudent.setDateOfBirth(studentEntity.getDateOfBirth());
        existingStudent.setGender(studentEntity.getGender());
        existingStudent.setAddress(studentEntity.getAddress());
        existingStudent.setEmail(studentEntity.getEmail());
        existingStudent.setPhone(studentEntity.getPhone());
        existingStudent.setStatus(studentEntity.getStatus());
        existingStudent.setClassEntity(studentEntity.getClassEntity());

        studentRepository.save(existingStudent);
        return "redirect:/student";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/student";
    }

}
