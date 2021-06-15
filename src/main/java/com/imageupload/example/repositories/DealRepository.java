package com.imageupload.example.repositories;

import com.imageupload.example.entity.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<DealEntity, Long> {
    
}
