package com.imageupload.example.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupUsersEntity extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private Long userId;
    private String userName;
    private String profilePath;

    @Enumerated(EnumType.STRING)
    private GroupUsersEnumType authorization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private GroupEntity groupEntity;

    public enum GroupUsersEnumType {
        manager, staff, member
    }

}
