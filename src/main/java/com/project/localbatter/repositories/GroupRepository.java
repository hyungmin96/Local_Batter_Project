package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepository extends JpaRepository<GroupEntity, Long>{

//    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = { "files" })
//    @Query("select distinct g from GroupEntity g left join fetch GroupEntity.files f")
//    Page<GroupEntity> findAllUsingPaging(Pageable page);

}
