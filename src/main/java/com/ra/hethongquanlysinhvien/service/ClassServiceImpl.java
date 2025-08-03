package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.enums.ClassStatus;
import com.ra.hethongquanlysinhvien.model.entity.ClassEntity;
import com.ra.hethongquanlysinhvien.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassServiceImpl implements ClassService{
    @Autowired
    private ClassRepository classRepository;

    @Override
    public long getTotalClasses() {
        return classRepository.count();
    }

    @Override
    public long getTotalActiveClasses() {
        return classRepository.countClassByStatus(ClassStatus.ACTIVE);
    }

    @Override
    public long getTotalCompletedClasses() {
        return classRepository.countClassByStatus(ClassStatus.COMPLETED);
    }

    @Override
    public long getTotalWaitingClasses() {
        return classRepository.countClassByStatus(ClassStatus.WAITING);
    }

    @Override
    public Page<ClassEntity> showListClass(String keyword, int page, int size, String sortBy) {
        // Tạo đối tượng phân trang và sắp xếp
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        // Nếu từ khóa không rỗng thì tìm theo từ khóa (VD: className hoặc teacherName)
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Lọc theo tên lớp hoặc tên giáo viên chứa từ khóa
            return classRepository.findAll((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("className")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("teacherName")), "%" + keyword.toLowerCase() + "%")
            ), pageable);
        }
        // Nếu không có từ khóa thì trả về toàn bộ danh sách theo phân trang
        return classRepository.findAll(pageable);
    }

    @Override
    public ClassEntity saveOrUpdateClass(ClassEntity classEntity) {
        return classRepository.save(classEntity);
    }

    @Override
    public void deleteClassById(Long id) {
        classRepository.deleteById(id);
    }

    @Override
    public List<ClassEntity> findAllClass() {
        return classRepository.findAll();
    }
}
