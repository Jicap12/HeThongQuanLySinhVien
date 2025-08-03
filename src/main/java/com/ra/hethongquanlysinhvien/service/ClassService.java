package com.ra.hethongquanlysinhvien.service;

import com.ra.hethongquanlysinhvien.model.entity.ClassEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClassService {
    long getTotalClasses();
    long getTotalActiveClasses();
    long getTotalCompletedClasses();
    long getTotalWaitingClasses();

    Page<ClassEntity> showListClass(String keyword, int page, int size, String sortBy);
    ClassEntity saveOrUpdateClass(ClassEntity classEntity);
    void deleteClassById(Long id);
    public List<ClassEntity> findAllClass();
}
