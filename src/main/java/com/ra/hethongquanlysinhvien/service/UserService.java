package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.model.dto.UserResponseDTO;
import com.ra.hethongquanlysinhvien.model.dto.UserRegisterDto;
import com.ra.hethongquanlysinhvien.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);
    Page<User> findAllAvailableUsers(String keyword, int page, int size, String sortBy, String direction);
    Optional<User> findById(Long id);

    Page<UserResponseDTO> showListUser(String keyword, int page, int size, String sortBy);

    void blockUser(Long id);
    void unblockUser(Long id);
}
