package com.project.localbatter.repositories;

import java.util.List;

import com.project.localbatter.entity.BoardEntity;

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

    @Query(value = "select * from BoardEntity order by rand() limit 6", nativeQuery = true)
    public List<BoardEntity> viewRandomBoardList();

    @Modifying
    @Query(value = "update BoardEntity set view = view + 1 where id = :id", nativeQuery = true)
    public void updateView(Long id);

    @Modifying
    @Query(value = "update BoardEntity set cart = cart + 1 where id = :id", nativeQuery = true)
    public void updateCart(Long id);
}
