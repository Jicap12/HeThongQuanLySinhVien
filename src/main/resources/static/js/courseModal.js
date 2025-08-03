function openAddCourseModal() {
    document.getElementById('addCourseModal').style.display = 'block';
}

function handleEditCourseClick(element) {
    const id = element.dataset.id;
    const code = element.dataset.code;
    const name = element.dataset.name;
    const duration = element.dataset.duration;
    const status = element.dataset.status;

    // Xóa các thông báo lỗi cũ
    const errorElements = document.querySelectorAll('#editCourseModal .error');
    errorElements.forEach(el => {
        el.textContent = '';
        el.style.display = 'none';
    });

    openEditCourseModal(id, code, name, duration, status);
}

// addCourseModal
function openEditCourseModal(id, code, name, duration, status) {
    document.querySelector('#editCourseModal input[name="id"]').value = id;
    document.querySelector('#editCourseModal input[name="courseCode"]').value = code;
    document.querySelector('#editCourseModal input[name="courseName"]').value = name;
    document.querySelector('#editCourseModal input[name="durationHours"]').value = duration;
    document.querySelector('#editCourseModal select[name="status"]').value = status === 'true' ? 'true' : 'false';
    document.getElementById('editCourseModal').style.display = 'block';
}

function handleDeleteCourseClick(element) {
    const id = element.dataset.id;
    const code = element.dataset.name;

    openDeleteCourseModal(id, code);
}

function openDeleteCourseModal(id, courseName) {
    document.getElementById('deleteCourseId').value = id;
    document.getElementById('deleteCourseNameDisplay').textContent = courseName;
    document.getElementById('deleteCourseModal').style.display = 'block';
}




