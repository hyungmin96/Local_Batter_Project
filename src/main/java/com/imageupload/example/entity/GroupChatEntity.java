package com.imageupload.example.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupChatEntity{

    @Id @GeneratedValue
    private Long id;
    private Long userId;
    private String message;
    private String sender;
    private String type;
    private String profilePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    @JsonIgnore
    private GroupEntity groupEntity;

    @CreationTimestamp
    private Timestamp localDate;

}
