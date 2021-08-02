package com.project.localbatter.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_chat")
public class GroupChatEntity extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "gchat_id")
    private Long id;

    @Column(name = "gchat_user_id")
    private Long user;

    @Column(name = "gchat_message")
    private String message;

    @Column(name = "gchat_type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gchat_group_id")
    private GroupEntity groupEntity;

}
