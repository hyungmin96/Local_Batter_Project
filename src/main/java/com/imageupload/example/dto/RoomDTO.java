package com.imageupload.example.dto;

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
public class RoomDTO {
    
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "roomDTO", cascade = CascadeType.ALL)
    private List<ChatDTO> chats = new ArrayList<>();

}
