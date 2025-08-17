package com.ra.hethongquanlysinhvien.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAvailableUserDto {
    private String userName;
    private String email;
    private Boolean status;
}
