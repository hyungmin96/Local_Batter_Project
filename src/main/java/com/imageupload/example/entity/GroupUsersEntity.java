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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupUsersEntity {

    @Id @GeneratedValue
    private Long id;

    private Long userId;
    private String user_name;
    private String profilePath;

    @Enumerated(EnumType.STRING)
    private GroupUsersEnumType authorization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private GroupEntity groupEntity;

    @CreationTimestamp
    private Timestamp regTime;

}
