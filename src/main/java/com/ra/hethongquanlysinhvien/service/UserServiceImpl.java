package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.model.dto.UserRegisterDto;
import com.ra.hethongquanlysinhvien.model.entity.Role;
import com.ra.hethongquanlysinhvien.model.entity.User;
import com.ra.hethongquanlysinhvien.repository.RoleRepository;
import com.ra.hethongquanlysinhvien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        Role defaultRole = roleRepository.findByRoleName("ROLE_USER");
        user.getRoles().add(defaultRole);

        userRepository.save(user);
    }
}
