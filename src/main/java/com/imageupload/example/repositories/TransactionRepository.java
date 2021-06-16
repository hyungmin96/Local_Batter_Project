package com.imageupload.example.repositories;

import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    public Page<TransactionEntity> findAllBySellerOrBuyer(UserEntity user1, UserEntity user2, Pageable request);
}
