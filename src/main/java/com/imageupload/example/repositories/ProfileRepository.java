package com.imageupload.example.repositories;

import com.imageupload.example.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>{

}
