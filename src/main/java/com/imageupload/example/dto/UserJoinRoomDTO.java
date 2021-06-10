package com.imageupload.example.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.imageupload.example.models.UserVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRoomDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "targetId")
    private UserVo target;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserVo userVo;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private RoomDTO roomDTO;

}
