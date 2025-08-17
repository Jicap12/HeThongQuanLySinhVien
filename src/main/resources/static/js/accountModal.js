
// Mở modal khóa user
function openBlockModal(element) {
    const userId = element.getAttribute("data-id");
    const userName = element.getAttribute("data-name");

    // Hiển thị tên user trong modal
    document.getElementById("blockUserNameDisplay").textContent = userName;
    // Đặt giá trị id vào trường ẩn
    document.getElementById("blockUserId").value = userId;

    // Hiển thị modal
    document.getElementById("blockUserModal").style.display = "block";
}

// Mở modal mở khóa user
function openUnblockModal(element) {
    const userId = element.getAttribute("data-id");
    const userName = element.getAttribute("data-name");

    document.getElementById("unblockUserNameDisplay").textContent = userName;
    document.getElementById("unblockUserId").value = userId;

    document.getElementById("unblockUserModal").style.display = "block";
}
