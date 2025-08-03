function openAddClassModal() {
    document.getElementById('addClassModal').style.display = 'block';
}

function handleEditClassClick(element) {
    const id = element.dataset.id || '';
    const code = element.dataset.code || '';
    const name = element.dataset.name || '';
    const teacherName = element.dataset.teacherName || '';
    const description = element.dataset.description || '';
    const numberOfStudents = element.dataset.numberOfStudents || '';
    const status = element.dataset.status || '';
    const course = element.dataset.course || '';
    console.log({ id, code, name, teacherName, description, numberOfStudents, status, course });

    // Xóa các thông báo lỗi cũ
    const errorElements = document.querySelectorAll('#editClassModal .error');
    errorElements.forEach(el => {
        el.textContent = '';
        el.style.display = 'none';
    });

    openEditClassModal(id, code, name, teacherName, description, numberOfStudents, status, course);
}

// editClassModal
function openEditClassModal(id, code, name, teacherName, description, numberOfStudents, status, course) {
    document.querySelector('#editClassModal input[name="id"]').value = id;
    document.querySelector('#editClassModal input[name="classCode"]').value = code;
    document.querySelector('#editClassModal input[name="className"]').value = name;
    document.querySelector('#editClassModal input[name="teacherName"]').value = teacherName;
    document.querySelector('#editClassModal textarea[name="description"]').value = description;
    document.querySelector('#editClassModal input[name="numberOfStudents"]').value = numberOfStudents;
    document.querySelector('#editClassModal select[name="status"]').value = status;
    document.querySelector('#editClassModal select[name="course.id"]').value = course;
    document.getElementById('editClassModal').style.display = 'block';
}

function handleDeleteClassClick(element) {
    const id = element.dataset.id || '';
    const code = element.dataset.name || '';

    openDeleteClassModal(id, code);
}

function openDeleteClassModal(id, className) {
    document.getElementById('deleteClassId').value = id;
    document.getElementById('deleteClassNameDisplay').textContent = className;
    document.getElementById('deleteClassModal').style.display = 'block';
}




