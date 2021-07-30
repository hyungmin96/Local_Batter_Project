package com.project.localbatter.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tbl_group")
@Builder
public class GroupEntity {

    @Column(name = "group_id")
    @Id @GeneratedValue
    private Long id;

    @Column(name = "group_title")
    private String groupTitle;

    @Column(name = "group_description")
    private String description;

    @Column(name = "group_owner")
    private String owner;

    @Column(name = "group_members")
    private int memberCount;

    @Column(name = "group_type")
    @Enumerated(EnumType.STRING)
    private roomEnumType type;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupFileEntity> files;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupUserJoinEntity> UserGroupJoin;

    public enum roomEnumType{
        공개, 일부공개, 비공개
    }

}
