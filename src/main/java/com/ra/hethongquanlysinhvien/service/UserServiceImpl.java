package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.enums.RoleList;
import com.ra.hethongquanlysinhvien.model.dto.UserRegisterDto;
import com.ra.hethongquanlysinhvien.model.entity.Role;
import com.ra.hethongquanlysinhvien.model.entity.User;
import com.ra.hethongquanlysinhvien.repository.RoleRepository;
import com.ra.hethongquanlysinhvien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByUserName(userRegisterDto.getUserName())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Mật khẩu không khớp");
        }

        User user = new User();
        user.setUserName(userRegisterDto.getUserName());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        Role defaultRole = roleRepository.findByRoleName(RoleList.ROLE_USER);
        user.getRoles().add(defaultRole);

        userRepository.save(user);
    }

    @Override
    public Page<User> findAllAvailableUsers(String keyword, int page, int size, String sortBy, String sortDir) {
        // Tạo đối tượng Sort dựa vào sortBy và sortDir
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        // Tạo đối tượng Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        if (keyword == null || keyword.trim().isEmpty()) {
            // Không có keyword → trả về toàn bộ user thỏa điều kiện
            return userRepository.findAllAvailableUsers(pageable);
        } else {
            // Có keyword → tìm kiếm theo keyword
            return userRepository.searchAllAvailableUsers(keyword.trim(), pageable);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}
