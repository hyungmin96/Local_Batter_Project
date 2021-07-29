package com.project.localbatter.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupUsersEntity extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"notification",""})
    private UserEntity user;

    @OneToMany(mappedBy = "groupUsersEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GroupChatEntity> groupChatEntity = new ArrayList<>();

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
