package com.imageupload.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyingUsersEntity {
    
    @Id @GeneratedValue
    private Long id;
    
    private String user;
    
    @ManyToOne
    @JoinColumn(name = "userRoomId")
    @JsonIgnore
    private BuyingChatRoomEntity buyingChatRoomEntity;

}
