package com.imageupload.example.JpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.imageupload.example.Vo.UserVo;

public interface UserRepository extends JpaRepository<UserVo, Long>{
    
    UserVo findByUsername(@Param("username") String username);

}
