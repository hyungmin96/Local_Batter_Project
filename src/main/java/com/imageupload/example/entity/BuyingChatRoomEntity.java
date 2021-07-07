package com.imageupload.example.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Builder
public class BuyingChatRoomEntity {
    
    @Id @GeneratedValue
    @Column(name = "roomId")
    private Long id;

    private String title;
    private String description;
    private String roomTitle;
    private String roomDate;
    private int limitUsers;
    private int price;
    private String owner;

}
