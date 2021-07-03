package com.imageupload.example.repositories;

import com.imageupload.example.entity.BoardEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
    public Page<BoardEntity> findAll(Pageable page);
    public Page<BoardEntity> findByTitleContaining(String title, Pageable page);
    public Page<BoardEntity> findByTitleContainingOrderByIdDesc(String title, Pageable page);
    public Page<BoardEntity> findAllBycategory(String category, Pageable page);

    @Modifying
    @Query(value = "update boardentity set view = view + 1 where id = :id", nativeQuery = true)
    public void updateView(Long id);

    @Modifying
    @Query(value = "update boardentity set cart = cart + 1 where id = :id", nativeQuery = true)
    public void updateCart(Long id);
}
