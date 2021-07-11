package com.imageupload.example.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
