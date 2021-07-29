package com.project.localbatter.repositories;

import com.project.localbatter.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>{

}
