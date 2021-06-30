package com.imageupload.example.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEntity implements Serializable {
    
    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "notification")
    private UserEntity userId;

    @Column(columnDefinition = "integer default 0")
    private int notification;

    @Column(columnDefinition = "integer default 0")
    private int transaction;

    @Column(columnDefinition = "integer default 0")
    private int chat;

}
