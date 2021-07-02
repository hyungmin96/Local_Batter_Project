package com.imageupload.example.repositories;

import com.imageupload.example.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>{
    @Modifying
    @Query(value = "update notificationentity set chat = chat + 1 where id = :id", nativeQuery = true)
    public void updateChat(Long id);

    @Modifying
    @Query(value = "update notificationentity set notification = notification + 1 where id = :id", nativeQuery = true)
    public void updateNotification(Long id);

    @Modifying
    @Query(value = "update notificationentity set transaction = transaction - 1 where id in(:sellerId, :buyerId)", nativeQuery = true)
    public void completeTransaction(Long sellerId, Long buyerId);

    @Modifying
    @Query(value = "update notificationentity set transaction = transaction + 1 where id  in(:sellerId, :buyerId)", nativeQuery = true)
    public void updateTransaction(Long sellerId, Long buyerId);

    @Modifying
    @Query(value = "update notificationentity set chat = 0 where id = :id", nativeQuery = true)
    public void clearChat(Long id);

    @Modifying
    @Query(value = "update notificationentity set notification = 0 where id = :id", nativeQuery = true)
    public void clearNotification(Long id);

    @Modifying
    @Query(value = "update notificationentity set transaction = 0 where id = :id", nativeQuery = true)
    public void clearTransaction(Long id);

}
