package com.ra.hethongquanlysinhvien.enums;

import lombok.Getter;

@Getter
public enum RoleList {
    ROLE_ADMIN("Admin"),
    ROLE_SUB_ADMIN("Sub Admin"),
    ROLE_USER("Người dùng"),
    ROLE_STUDENT("Học viên"),
    ROLE_TEACHER("Giảng viên");

    private final String descriptionRole;

    RoleList(String roleName) {
        this.descriptionRole = roleName;
    }

}
