package com.dio.springlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dio.springlearn.models.AuthModels;

@Repository
public interface AuthRepo extends JpaRepository<AuthModels, Long> {
    @Query(value = "SELECT * FROM users where email = :email", nativeQuery=true)
    AuthModels findUserByEmail(@Param(value= "email") String email);

}
