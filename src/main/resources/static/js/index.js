
// Đóng modal
function closeModals() {
    const modals = ['addCourseModal', 'editCourseModal', 'deleteCourseModal','addClassModal', 'editClassModal', 'deleteClassModal', 'addStudentModal', 'editStudentModal', 'deleteStudentModal'];
    modals.forEach(id => {
        const modal = document.getElementById(id);
        if (modal) {
            modal.style.display = 'none';
            resetForm(id);
        }
    });
}

// Đóng modal khi click bên ngoài
window.onclick = function(event) {
    const modals = ['addCourseModal', 'editCourseModal', 'deleteCourseModal','addClassModal', 'editClassModal', 'deleteClassModal', 'addStudentModal', 'editStudentModal', 'deleteStudentModal'];
    modals.forEach(id => {
        const modal = document.getElementById(id);
        if (event.target === modal) {
            modal.style.display = "none";
            resetForm(id);
        }
    });
}

// Reset form và xóa lỗi
function resetForm(modalId) {
    const form = document.querySelector(`#${modalId} form`);

    if (form) {
        // Xóa trắng tất cả input, select, textarea (trừ type="hidden")
        form.querySelectorAll('input:not([type="hidden"]), textarea, select').forEach(input => {
            if (input.tagName === 'SELECT') {
                input.selectedIndex = 0; // Đặt về option đầu tiên
            } else {
                input.value = ''; // Xóa giá trị
            }
        });
    }

    // Xóa thông báo lỗi
    const errorElements = document.querySelectorAll(`#${modalId} .error`);
    errorElements.forEach(el => {
        el.textContent = '';
        el.style.display = 'none';
    });

}