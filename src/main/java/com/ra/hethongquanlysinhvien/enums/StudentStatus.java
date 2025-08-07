package com.ra.hethongquanlysinhvien.enums;

import lombok.Getter;

@Getter
public enum StudentStatus {
    WAITING_FOR_CLASS("Chờ lớp"),
    STUDYING("Đang học"),
    DEFERRED("Bảo lưu"),
    SUSPENDED("Đình chỉ"),
    GRADUATED("Đã tốt nghiệp");

    //description là thuộc tính dùng để gán mô tả tiếng Việt cho từng trạng thái.
    private final String description;

    StudentStatus(String description) {
        this.description = description;
    }

}

