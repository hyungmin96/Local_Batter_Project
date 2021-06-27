package com.imageupload.example.repositories;

import com.imageupload.example.entity.NotificationEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>{
    // @Query(value = "", nativeQuery = true)
    // public int findBychat();
}
