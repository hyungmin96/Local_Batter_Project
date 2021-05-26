package com.imageupload.example.JpaRepositories;

import java.util.List;
import java.util.Optional;

import com.imageupload.example.Vo.boardVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface boardRepository extends JpaRepository<boardVo, Integer>{

    public List<boardVo> findAllByOrderByIdDesc();
    public List<boardVo> findByTitleContainingOrderByIdDesc(String title);
}
