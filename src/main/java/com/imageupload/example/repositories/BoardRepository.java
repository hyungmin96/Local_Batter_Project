package com.imageupload.example.repositories;

import com.imageupload.example.entity.BoardEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
    public Page<BoardEntity> findAll(Pageable page);
    public Page<BoardEntity> findByTitleContaining(String title, Pageable page);
    public Page<BoardEntity> findByTitleContainingOrderByIdDesc(String title, Pageable page);
    public Page<BoardEntity> findAllBycategory(String category, Pageable page);
}
