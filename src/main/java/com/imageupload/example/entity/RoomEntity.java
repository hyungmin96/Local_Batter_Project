package com.imageupload.example.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class RoomEntity {
    
    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "roomEntity", cascade = CascadeType.ALL)
    private List<ChatEntity> chats = new ArrayList<>();

}
