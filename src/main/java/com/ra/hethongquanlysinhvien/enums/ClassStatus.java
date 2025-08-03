package com.ra.hethongquanlysinhvien.enums;

public enum ClassStatus {
    WAITING("Chờ lớp"),
    ACTIVE("Đang hoạt động"),
    COMPLETED("Đã kết thúc");

    //description là thuộc tính dùng để gán mô tả tiếng Việt cho từng trạng thái.
    private final String description;

    ClassStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

