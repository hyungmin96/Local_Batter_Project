package com.project.localbatter.entity;

import lombok.*;

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

    @Column(name = "group_tag")
    private String tag;

    @Column(name = "group_type")
    @Enumerated(EnumType.STRING)
    private roomEnumType type;

    @Column(name = "group_file_path")
    private String filePath;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupUserJoinEntity> UserGroupJoin;

    @OneToMany(mappedBy = "groupEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupChatEntity> chats;

    public enum roomEnumType{
        공개, 일부공개, 비공개
    }

    public void updateInfo(String groupTitle, String description, String filePath){
        this.groupTitle = groupTitle;
        this.description = description;
        this.filePath = (filePath != null) ? filePath : this.filePath;
    }

}
