function openAddStudentModal(element) {
    resetForm('addStudentModal');
    const userId = element.getAttribute('data-user-id') || '';
    const userEmail = element.getAttribute('data-user-email') || '';
    document.getElementById('studentEmail').value = userEmail;
    document.getElementById('studentUserId').value = userId;
    document.getElementById('addStudentModal').style.display = 'block';
}

function handleEditStudentClick(element) {
    const id = element.dataset.id || '';
    const studentCode = element.dataset.studentCode || '';
    const studentName = element.dataset.studentName || '';
    const dateOfBirth = element.dataset.dateOfBirth || '';
    const gender = element.dataset.gender || '';
    const address = element.dataset.address || '';
    const email = element.dataset.email || '';
    const phone = element.dataset.phone || '';
    const status = element.dataset.status || '';
    const classId = element.dataset.classId || '';

    // Clear old error messages
    const errorElements = document.querySelectorAll('#editStudentModal .error');
    errorElements.forEach(el => {
        el.textContent = '';
        el.style.display = 'none';
    });

    openEditStudentModal(id, studentCode, studentName, dateOfBirth, gender, address, email, phone, status, classId);
}

function openEditStudentModal(id, studentCode, studentName, dateOfBirth, gender, address, email, phone, status, classId) {
    document.querySelector('#editStudentModal input[name="id"]').value = id;
    document.querySelector('#editStudentModal input[name="studentCode"]').value = studentCode;
    document.querySelector('#editStudentModal input[name="studentName"]').value = studentName;
    document.querySelector('#editStudentModal input[name="dateOfBirth"]').value = dateOfBirth;
    document.querySelector('#editStudentModal select[name="gender"]').value = gender;
    document.querySelector('#editStudentModal input[name="address"]').value = address;
    document.querySelector('#editStudentModal input[name="email"]').value = email;
    document.querySelector('#editStudentModal input[name="phone"]').value = phone;
    document.querySelector('#editStudentModal select[name="status"]').value = status;
    document.querySelector('#editStudentModal select[name="classEntity.id"]').value = classId;
    document.getElementById('editStudentModal').style.display = 'block';
}

function handleDeleteStudentClick(element) {
    const id = element.dataset.id || '';
    const studentName = element.dataset.studentName || '';

    openDeleteStudentModal(id, studentName);
}

function openDeleteStudentModal(id, studentName) {
    document.getElementById('deleteStudentId').value = id;
    document.getElementById('deleteStudentNameDisplay').textContent = studentName;
    document.getElementById('deleteStudentModal').style.display = 'block';
}




