package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupChatEntity;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.entity.GroupUsersEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupUsersRepository extends JpaRepository<GroupUsersEntity, Long>{


}
