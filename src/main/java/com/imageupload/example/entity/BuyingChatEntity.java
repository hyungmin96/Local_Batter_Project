package com.imageupload.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyingChatEntity {

    @Id @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private int numbers;
    private int price;
    
}
