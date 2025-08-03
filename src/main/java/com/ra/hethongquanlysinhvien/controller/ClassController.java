package com.ra.hethongquanlysinhvien.controller;

import com.ra.hethongquanlysinhvien.enums.ClassStatus;
import com.ra.hethongquanlysinhvien.model.entity.ClassEntity;
import com.ra.hethongquanlysinhvien.model.entity.Course;
import com.ra.hethongquanlysinhvien.repository.ClassRepository;
import com.ra.hethongquanlysinhvien.service.ClassService;
import com.ra.hethongquanlysinhvien.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ClassRepository classRepository;

    @GetMapping
    public String showClassPage(@RequestParam(name = "keyword", required = false) String keyword, // Từ khóa tìm kiếm (nếu có)
                                @RequestParam(name = "page", defaultValue = "0") int page, // Trang hiện tại
                                @RequestParam(name = "size", defaultValue = "5") int size, // Số dòng mỗi trang
                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy, // Sắp xếp theo trường nào
                                Model model) {

        // Gọi service để lấy danh sách lớp theo phân trang, tìm kiếm
        Page<ClassEntity> classPage = classService.showListClass(keyword, page, size, sortBy);

        // Đưa dữ liệu ra view
        model.addAttribute("classPage", classPage); // Toàn bộ thông tin phân trang
        model.addAttribute("currentPage", page); // Trang hiện tại
        model.addAttribute("totalPages", classPage.getTotalPages()); // Tổng số trang
        model.addAttribute("keyword", keyword); // Từ khóa tìm kiếm hiện tại
        model.addAttribute("sortBy", sortBy); // Trường sắp xếp

        model.addAttribute("classEntity", new ClassEntity());
        model.addAttribute("classStatuses", ClassStatus.values()); // Danh sách trạng thái lớp học
        model.addAttribute("courses", courseService.findAllCourse()); // Danh sách tất cả các khóa học trong DB

        return "class";
    }
    @PostMapping("/add") // Xử lý khi người dùng submit form thêm mới lớp học
    public String addClass(
            @ModelAttribute("classEntity") @Valid ClassEntity classEntity, // Lấy dữ liệu từ form và kiểm tra validation
            BindingResult result, // Kết quả kiểm tra hợp lệ
            Model model, // Model để truyền dữ liệu ra view
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy
    ) {
        // Nếu có lỗi trong form (ví dụ thiếu tên lớp, sai định dạng, v.v.)
        if (result.hasErrors()) {
            // Load lại danh sách lớp (phân trang) để hiển thị trong view
            Page<ClassEntity> classPage = classService.showListClass(keyword, page, size, sortBy);
            model.addAttribute("classPage", classPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", classPage.getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("classStatuses", ClassStatus.values());
            model.addAttribute("courses", courseService.findAllCourse());
            model.addAttribute("classEntity", classEntity);
            model.addAttribute("openAddClassModal", true);
            return "class"; // Trả lại form và hiển thị lỗi
        }

        // Nếu không có lỗi, lưu dữ liệu lớp mới
        classService.saveOrUpdateClass(classEntity);

        // Redirect để tránh submit lại khi reload
        return "redirect:/class";
    }

    @PostMapping("/edit")
    public String editClass(@ModelAttribute("classEntity") @Valid ClassEntity classEntity,
                            BindingResult result,
                            @RequestParam("id") Long id,
                            Model model,
                            @RequestParam(name = "keyword", required = false) String keyword,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "5") int size,
                            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
        ClassEntity existingClass = classRepository.findById(id).orElse(null);

        if (existingClass == null) {
            return "redirect:/class";
        }

        // Kiểm tra trùng mã (chỉ khi mã thay đổi)
        if (!existingClass.getClassCode().equals(classEntity.getClassCode())) {
            if (classRepository.existsByClassCode(classEntity.getClassCode())) {
                result.rejectValue("classCode", "duplicate.classCode", "Mã lớp học đã tồn tại");
            }
        }

        // Kiểm tra trùng tên (chỉ khi tên thay đổi)
        if (!existingClass.getClassName().equals(classEntity.getClassName())) {
            if (classRepository.existsByClassName(classEntity.getClassName())) {
                result.rejectValue("className", "duplicate.className", "Tên lớp học đã tồn tại");
            }
        }

        if (result.hasErrors()) {
            // Giữ lại dữ liệu phân trang
            model.addAttribute("classPage", classService.showListClass(keyword, page, size, sortBy));
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", classService.showListClass(keyword, page, size, sortBy).getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("classStatuses", ClassStatus.values());
            model.addAttribute("courses", courseService.findAllCourse()); // Thêm danh sách khóa học
            model.addAttribute("classEntity", classEntity); // Giữ dữ liệu đã nhập
            model.addAttribute("openEditClassModal", true);
            return "class";
        }

        // Cập nhật lại thông tin mới
        existingClass.setClassCode(classEntity.getClassCode());
        existingClass.setClassName(classEntity.getClassName());
        existingClass.setTeacherName(classEntity.getTeacherName());
        existingClass.setDescription(classEntity.getDescription());
        existingClass.setNumberOfStudents(classEntity.getNumberOfStudents());
        existingClass.setStatus(classEntity.getStatus());
        existingClass.setCourse(classEntity.getCourse());

        classRepository.save(existingClass);
        return "redirect:/class";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        classService.deleteClassById(id);
        return "redirect:/class";
    }

    }





//    @PostMapping("/edit")
//    public String editClass(
//            @ModelAttribute("classEntity") @Valid ClassEntity classEntity,
//            BindingResult result,
//            Model model,
//            @RequestParam(name = "keyword", required = false) String keyword,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size,
//            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy
//    ) {
//        if (result.hasErrors()) {
//            // Load lại danh sách để hiển thị
//            Page<ClassEntity> classPage = classService.showListClass(keyword, page, size, sortBy);
//            model.addAttribute("classPage", classPage);
//            model.addAttribute("currentPage", page);
//            model.addAttribute("totalPages", classPage.getTotalPages());
//            model.addAttribute("keyword", keyword);
//            model.addAttribute("sortBy", sortBy);
//            model.addAttribute("classStatuses", ClassStatus.values());
//            model.addAttribute("courses", courseService.findAllCourse());
//
//            // Đánh dấu để mở modal edit với dữ liệu lỗi
//            model.addAttribute("openEditClassModal", true);
//            return "class";
//        }
//
//        // Lưu thông tin đã chỉnh sửa
//        classService.saveOrUpdateClass(classEntity);
//        return "redirect:/class";
//    }
//
//    // Thêm method xử lý POST cho xóa lớp học
//    @PostMapping("/delete")
//    public String deleteClass(@RequestParam("id") Long id) {
//        classService.deleteClassById(id);
//        return "redirect:/class";
//    }

