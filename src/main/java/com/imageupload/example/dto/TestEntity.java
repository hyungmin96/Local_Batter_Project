package com.imageupload.example.dto;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import com.imageupload.example.entity.ChatEntity;

public class TestEntity {

    @PostLoad
    public void postLoad(ChatEntity dto) {
        System.out.println("postLoad");
    }
    
    @PrePersist
    public void prePersist(ChatEntity dto) {
        // System.out.println("prePersist");
    }
    
    @PostPersist
    public void postPersist(ChatEntity dto) {
        System.out.println("new Date().getTime()");
        // dto.setNewMessageTime(LocalDateTime.now());
    }
    
    @PreUpdate
    public void preUpdate(ChatEntity dto) {
        System.out.println("preUpdate");
    }
    
    @PostUpdate
    public void postUpdate(ChatEntity dto) {
        System.out.println("postUpdate");
    }
    
    @PreRemove
    public void preRemove(ChatEntity dto) {
        System.out.println("preRemove");
    }
    
    @PostRemove
    public void postRemove(ChatEntity dto) {
        System.out.println("PostRemove");
    }

}
