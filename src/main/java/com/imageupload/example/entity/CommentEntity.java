package com.imageupload.example.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Builder
public class CommentEntity {
    
    @Id @GeneratedValue
    private Long id;

    private String writer;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity commentUser;

    private String comment;

    @CreationTimestamp
    private Timestamp regDate;

}
