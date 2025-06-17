package com.ra.hethongquanlysinhvien.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name",length = 100,unique = true)
    private String userName;
    @Column(name = "email",length = 50)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private Boolean status;
}
