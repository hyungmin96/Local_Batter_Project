package com.project.localbatter.repositories;

import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.entity.BoardEntity;
import com.project.localbatter.entity.TransactionEntity;
import com.project.localbatter.enumtype.TransactionEnumType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    public Page<TransactionEntity> findAllByBuyerOrSeller(UserEntity user1, UserEntity user2, Pageable request);
    public TransactionEntity findByBuyerAndSellerAndBoardId(UserEntity buyer, UserEntity seller, BoardEntity Board);
    @Query(value="select * from TransactionEntity where type =:type and buyer =:user or type =:type and seller =:user", nativeQuery = true)
    public Page<TransactionEntity> findAllTransaction(@Param("type") String type, @Param("user") Long user, Pageable request);
    public Page<TransactionEntity> findAllByBuyerAndType(UserEntity Buyer, TransactionEnumType type, Pageable request);
}