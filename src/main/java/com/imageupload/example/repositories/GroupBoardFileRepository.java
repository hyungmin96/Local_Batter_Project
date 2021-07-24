package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupBoardFileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupBoardFileRepository  extends JpaRepository<GroupBoardFileEntity, Long> {
    Page<GroupBoardFileEntity> findTop9BygroupId(Long groupId, Pageable request);
}
