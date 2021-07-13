package com.imageupload.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyingFileEntity {

    @Id @GeneratedValue
    private Long id;
    private String path;
    private String name;

    @ManyToOne
    @JoinColumn(name = "roomId")
    @JsonIgnore
    private BuyingChatRoomEntity buyingChatRoomEntity;

    @CreationTimestamp
    private Timestamp regTime;
}
