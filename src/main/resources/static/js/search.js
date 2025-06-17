
function clearSearch() {
    const input = document.getElementById("searchInput");
    input.value = "";
    input.focus();
    input.dispatchEvent(new Event('input')); // Gọi lại các sự kiện nếu có lọc
}

    // Hiện hoặc ẩn nút "x" khi người dùng gõ
document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("searchInput");
    const clearBtn = document.querySelector(".clear-btn");

    function toggleClearBtn() {
    clearBtn.style.display = input.value ? "inline" : "none";
}

input.addEventListener("input", toggleClearBtn);
    toggleClearBtn(); // Kiểm tra ban đầu
});

