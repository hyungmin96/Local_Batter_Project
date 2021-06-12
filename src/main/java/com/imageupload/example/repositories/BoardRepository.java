package com.imageupload.example.repositories;

import com.imageupload.example.entity.boardEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<boardEntity, Integer>{

    public Page<boardEntity> findAll(Pageable page);
    // PageRequest 로 page 인수설정시 오류가 남 2개의 파라미터에서는 Pageable로 선언하자
    public Page<boardEntity> findByTitleContaining(String title, Pageable page);
    public Page<boardEntity> findByTitleContainingOrderByIdDesc(String title, Pageable page);
    public Page<boardEntity> findAllBycategory(String category, Pageable page);
}
