function openAddModal() {
    document.getElementById('addModal').style.display = 'block';
}

function handleEditClick(element) {
    const id = element.dataset.id;
    const code = element.dataset.code;
    const name = element.dataset.name;
    const duration = element.dataset.duration;
    const status = element.dataset.status;

    // Xóa các thông báo lỗi cũ
    const errorElements = document.querySelectorAll('#editModal .error');
    errorElements.forEach(el => {
        el.textContent = '';
        el.style.display = 'none';
    });

    openEditModal(id, code, name, duration, status);
}

// addCourseModal
function openEditModal(id, code, name, duration, status) {
    document.querySelector('#editModal input[name="id"]').value = id;
    document.querySelector('#editModal input[name="courseCode"]').value = code;
    document.querySelector('#editModal input[name="courseName"]').value = name;
    document.querySelector('#editModal input[name="durationHours"]').value = duration;
    document.querySelector('#editModal select[name="status"]').value = status === 'true' ? 'true' : 'false';
    document.getElementById('editModal').style.display = 'block';
}

function handleDeleteClick(element) {
    const id = element.dataset.id;
    const code = element.dataset.name;

    openDeleteModal(id, code);
}

function openDeleteModal(id, courseName) {
    document.getElementById('deleteCourseId').value = id;
    document.getElementById('deleteCourseNameDisplay').textContent = courseName;
    document.getElementById('deleteModal').style.display = 'block';
}




// Đóng modal
function closeModals() {
    const modals = ['addModal', 'editModal', 'deleteModal'];
    modals.forEach(id => {
        const modal = document.getElementById(id);
        if (modal) {
            modal.style.display = 'none';
        }
    });
}

// Đóng modal khi click bên ngoài
window.onclick = function(event) {
    const modals = ['addModal', 'editModal', 'deleteModal'];
    modals.forEach(id => {
        const modal = document.getElementById(id);
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
}
