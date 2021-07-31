package com.project.localbatter.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_user_join")
public class GroupUserJoinEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_authority")
    private userAuthority type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_table")
    private UserEntity user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_table")
    private GroupEntity group;

    @OneToMany(mappedBy = "groupUserJoinEntity", fetch = LAZY, cascade = CascadeType.ALL)
    private List<GroupBoardEntity> board;

    @OneToMany(mappedBy = "groupUserJoinEntity", fetch = LAZY, cascade = CascadeType.ALL)
    private List<GroupCommentEntity> comment;

    public enum userAuthority{
        manager, staff, member, ban
    }

}
