package com.imageupload.example.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.imageupload.example.enumtype.BuyingUsersEnumType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyingUsersEntity {
    
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private BuyingUsersEnumType authorization;

    @ManyToOne
    @JoinColumn(name = "userRoomId")
    @JsonIgnore
    private BuyingChatRoomEntity buyingChatRoomEntity;

    @CreationTimestamp
    private Timestamp regTime;
}
