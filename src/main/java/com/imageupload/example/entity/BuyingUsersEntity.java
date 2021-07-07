package com.imageupload.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
public class BuyingUsersEntity {
    
    @Id @GeneratedValue
    private Long id;
    
    private String user;
    private Long roomId;

}
