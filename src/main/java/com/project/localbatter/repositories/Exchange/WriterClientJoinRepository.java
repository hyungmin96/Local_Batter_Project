package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WriterClientJoinRepository extends JpaRepository<WriterClientJoinEntity, Long> {
    @Query(value = "select c from WriterClientJoinEntity c where c.clientExchangeEntity.id = :clientExchangeId")
    WriterClientJoinEntity findByClientExchangeEntity(Long clientExchangeId);
}
