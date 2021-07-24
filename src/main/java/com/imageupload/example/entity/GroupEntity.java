package com.imageupload.example.entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupEntity extends BaseTimeEntity implements Serializable{
    
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private int currentUsers;
    private String owner;

    @Enumerated(EnumType.STRING)
    private roomEnumType type;

    public enum roomEnumType{
        공개, 일부공개, 비공개
    }

    @OneToMany(mappedBy = "groupEntity")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<GroupFileEntity> files;

    @OneToMany(mappedBy = "groupEntity")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<GroupChatEntity> chats;

    @OneToMany(mappedBy = "groupEntity")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<GroupUsersEntity> users;
}
