package com.imageupload.example.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.UserJoinRommEnumType;

import org.springframework.data.annotation.LastModifiedDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRoomEntity implements Serializable{
    
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserJoinRommEnumType userConnectionType;

    @Enumerated(EnumType.STRING)
    private UserJoinRommEnumType targetConnectionType;

    @ManyToOne
    @JoinColumn(name = "targetId")
    private UserEntity target;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userVo;

    @ManyToOne
    @JoinColumn(name = "createRoomId")
    private RoomEntity roomEntity;

    @LastModifiedDate
    private LocalDateTime newMessageTime;

}
