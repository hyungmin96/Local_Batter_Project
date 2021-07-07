package com.imageupload.example.entity;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "buyingChatRoomEntity")
    private List<BuyingFileEntity> files;

    @OneToMany(mappedBy = "buyingChatRoomEntity")
    private List<BuyingChatEntity> chats;

    @OneToMany(mappedBy = "buyingChatRoomEntity")
    private List<BuyingUsersEntity> users;
}
