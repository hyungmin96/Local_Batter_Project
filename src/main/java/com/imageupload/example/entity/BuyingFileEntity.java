package com.imageupload.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Builder
public class BuyingFileEntity {

    @Id @GeneratedValue
    private Long id;
    private String path;
    private String name;

}
