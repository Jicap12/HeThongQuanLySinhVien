
// Đóng modal
function closeModals() {
    const modals = ['addCourseModal', 'editCourseModal', 'deleteCourseModal','addClassModal', 'editClassModal', 'deleteClassModal'];
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
    const modals = ['addCourseModal', 'editCourseModal', 'deleteCourseModal','addClassModal', 'editClassModal', 'deleteClassModal'];
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
    if (form) form.reset(); // Reset tất cả trường về giá trị mặc định
    const errorElements = document.querySelectorAll(`#${modalId} .error`);
    errorElements.forEach(el => {
        el.textContent = '';
        el.style.display = 'none';
    });
}