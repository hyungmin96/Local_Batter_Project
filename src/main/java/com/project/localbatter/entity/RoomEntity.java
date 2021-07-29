package com.project.localbatter.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class RoomEntity {
    
    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "roomEntity", fetch = FetchType.LAZY)
    private List<ChatEntity> chats = new ArrayList<>();

}
