package com.imageupload.example.JpaRepositories;

import com.imageupload.example.Vo.boardVo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<boardVo, Integer>{

    public Page<boardVo> findAll(Pageable page);
    // PageRequest 로 page 인수설정시 오류가 남 2개의 파라미터에서는 Pageable로 선언하자
    public Page<boardVo> findByTitleContaining(String title, Pageable page);
    public Page<boardVo> findByTitleContainingOrderByIdDesc(String title, Pageable page);
}
