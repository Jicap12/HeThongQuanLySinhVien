package com.ra.hethongquanlysinhvien.repository;

import com.ra.hethongquanlysinhvien.enums.StudentStatus;
import com.ra.hethongquanlysinhvien.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<StudentEntity, Long>, JpaSpecificationExecutor<StudentEntity> {
    long countStudentByStatus(StudentStatus studentStatus);
    boolean existsByStudentCode(String studentCode);
    // Kiểm tra trùng mã sinh viên nhưng bỏ qua id hiện tại (khi update)
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM StudentEntity s " +
            "WHERE s.studentCode = :studentCode AND s.id <> :excludeId")
    boolean existsByStudentCodeAndIdNot(@Param("studentCode") String studentCode,
                                        @Param("excludeId") Long excludeId);

    StudentEntity findStudentEntitiesById(Long id);
}
