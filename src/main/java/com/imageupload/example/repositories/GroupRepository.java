package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface GroupRepository extends JpaRepository<GroupEntity, Long>{
    @Transactional
    @Modifying
    @Query(value = "update GroupEntity set currentUsers = currentUsers + 1 where id =:id", nativeQuery = true)
    void enterCurrentUsers(Long id);

    @Transactional
    @Modifying
    @Query(value = "update GroupEntity set currentUsers = currentUsers - 1 where id =:id", nativeQuery = true)
    void exitCurrentUsers(Long id);
}
