package com.project.localbatter.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_chat")
public class GroupChatEntity extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @Column(name = "chat_message")
    private String message;

    @Column(name = "chat_type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    @JsonIgnore
    private GroupEntity groupEntity;

}
