package com.imageupload.example.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetId")
    private UserEntity target;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity userVo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createRoomId")
    private RoomEntity roomEntity;

    @LastModifiedDate
    private LocalDateTime newMessageTime;

}
