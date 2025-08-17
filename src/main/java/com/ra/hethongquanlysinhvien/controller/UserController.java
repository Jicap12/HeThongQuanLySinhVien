package com.ra.hethongquanlysinhvien.controller;


import com.ra.hethongquanlysinhvien.model.dto.UserResponseDTO;
import com.ra.hethongquanlysinhvien.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class UserController {

    private final UserService userService; // gọi Service đã viết

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Hiển thị danh sách user
    @GetMapping
    public String listUsers(
            @RequestParam(name = "keyword", required = false) String keyword,  // tìm kiếm
            @RequestParam(defaultValue = "0") int page,                         // trang mặc định = 0
            @RequestParam(defaultValue = "5") int size,                         // số bản ghi mỗi trang
            @RequestParam(defaultValue = "userName") String sortBy,             // field sắp xếp mặc định
            Model model) {

        // Gọi service lấy danh sách
        Page<UserResponseDTO> usersPage = userService.showListUser(keyword, page, size, sortBy);

        // Đẩy data sang Thymeleaf
        model.addAttribute("usersPage", usersPage); // danh sách user
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("keyword", keyword);          // giữ lại keyword để hiển thị
        model.addAttribute("sortBy", sortBy);

        return "accountList";
    }

    @PostMapping("/block")
    public String blockUser(
            @RequestParam("id") Long id,
            RedirectAttributes redirectAttributes) {

        try {
            userService.blockUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã khóa user thành công!");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/account";
    }

    @PostMapping("/unblock")
    public String unblockUser(
            @RequestParam("id") Long id,
            RedirectAttributes redirectAttributes) {
        try {
            userService.unblockUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã mở khóa user thành công!");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/account";
    }
}
