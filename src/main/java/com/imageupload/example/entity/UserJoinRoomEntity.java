package com.imageupload.example.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRoomEntity{
    
    @Id @GeneratedValue
    private Long id;

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
