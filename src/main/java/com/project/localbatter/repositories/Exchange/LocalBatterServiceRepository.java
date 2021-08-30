package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.Exchange.LocalBatterServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocalBatterServiceRepository extends JpaRepository<LocalBatterServiceEntity, Long> {

    @Query(value = "delete from LocalBatterServiceEntity l where l.writerClientJoinId = :writerClientJoinId")
    void deleteBywriterClientJoinId(Long writerClientJoinId);

}
