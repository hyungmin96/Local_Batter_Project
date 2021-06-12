package com.imageupload.example.repositories;

import com.imageupload.example.entity.BoardEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>{

    public Page<BoardEntity> findAll(Pageable page);
    // PageRequest 로 page 인수설정시 오류가 남 2개의 파라미터에서는 Pageable로 선언하자
    public Page<BoardEntity> findByTitleContaining(String title, Pageable page);
    public Page<BoardEntity> findByTitleContainingOrderByIdDesc(String title, Pageable page);
    public Page<BoardEntity> findAllBycategory(String category, Pageable page);
}
