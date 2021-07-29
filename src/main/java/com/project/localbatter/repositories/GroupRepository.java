package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface GroupRepository extends JpaRepository<GroupEntity, Long>{

    @Transactional
    @Modifying
    @Query(value = "update GroupEntity set memberCount = memberCount + 1 where id = :groupId")
    void memberCountUp(Long groupId);

}
