package com.ra.hethongquanlysinhvien.model.entity;

import com.ra.hethongquanlysinhvien.enums.RoleList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Lưu dạng text: ADMIN, STUDENT, TEACHER...
    @Column(name = "name", nullable = false, unique = true)
    private RoleList roleName;
}
