package com.imageupload.example.dto;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.imageupload.example.models.UserVo;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class ChatDTO {
    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @CreationTimestamp
    private Timestamp createTime;

    @ManyToOne
    @JoinColumn(name = "Id")
    private UserVo userVo;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private RoomDTO roomDTO;

}
