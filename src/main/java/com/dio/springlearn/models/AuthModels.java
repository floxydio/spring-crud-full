package com.dio.springlearn.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class AuthModels {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="users_id")
    private int usersId;

    @Column(name="name")
    private String name;

    @Email(message="Email is need @")
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="status")
    private int status;

    @Column(name="created_at")
    private String createdAt;

    
    @Column(name="updated_at")
    private String updatedAt;




    
}
