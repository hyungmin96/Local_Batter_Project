package com.imageupload.example.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {

    @Id @GeneratedValue
    private Long id;

    private String message;

    @CreationTimestamp
    private Timestamp createTime;

    @ManyToOne
    @JoinColumn(name = "sendId")
    private UserEntity userVo;

    @ManyToOne
    @JoinColumn(name = "roomId")
    @JsonIgnore
    private RoomEntity roomEntity;

}
