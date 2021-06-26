package com.imageupload.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.imageupload.example.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByUsername(String username);
}
