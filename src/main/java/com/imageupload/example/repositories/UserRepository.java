package com.imageupload.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import com.imageupload.example.models.UserVo;

public interface UserRepository extends JpaRepository<UserVo, Long>{
    Optional<UserVo> findByUsername(@Param("username") String username);
}
