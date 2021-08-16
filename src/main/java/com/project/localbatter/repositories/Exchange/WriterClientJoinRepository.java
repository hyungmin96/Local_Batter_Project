package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WriterClientJoinRepository extends JpaRepository<WriterClientJoinEntity, Long> {
    @Query(value = "select wc from WriterClientJoinEntity wc " +
            "join fetch wc.clientExchangeEntity c " +
            "join fetch wc.writerExchangeEntity w " +
            " where wc.clientExchangeEntity.id = :clientExchangeId")
    WriterClientJoinEntity findByClientExchangeEntity(Long clientExchangeId);
}
