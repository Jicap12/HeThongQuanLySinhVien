package com.ra.hethongquanlysinhvien.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegisterDto {
    @NotBlank
    @Size(min = 4, max = 20)
    private String userName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 6, message = "Mật khẩu phải ít nhất 6 ký tự")
    private String password;
    private boolean status = true;
    @NotBlank
    private String confirmPassword;
}
