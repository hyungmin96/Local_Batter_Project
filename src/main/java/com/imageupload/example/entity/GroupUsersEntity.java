package com.imageupload.example.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.imageupload.example.enumtype.GroupUsersEnumType;
import groovy.lang.Lazy;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupUsersEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private GroupUsersEnumType authorization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userRoomId")
    @JsonIgnore
    private GroupChatRoomEntity groupChatRoomEntity;

    @OneToMany(mappedBy = "groupUsersEntity")
    @JsonIgnore
    private List<GroupBoardEntity> groupBoardEntity;

    @CreationTimestamp
    private Timestamp regTime;

}
