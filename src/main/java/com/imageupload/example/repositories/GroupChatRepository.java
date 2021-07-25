package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupChatEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupChatRepository extends JpaRepository<GroupChatEntity,Long>{

}
