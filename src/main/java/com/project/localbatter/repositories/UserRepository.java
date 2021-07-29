package com.project.localbatter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.project.localbatter.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByUsername(String username);
}
