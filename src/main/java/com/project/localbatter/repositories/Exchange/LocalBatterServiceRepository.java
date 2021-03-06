package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.Exchange.LocalBatterServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LocalBatterServiceRepository extends JpaRepository<LocalBatterServiceEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from LocalBatterServiceEntity l where l.writerClientJoinId = :writerClientJoinId")
    void deleteBywriterClientJoinId(Long writerClientJoinId);

    List<LocalBatterServiceEntity> findTop5ByOrderById();
}
