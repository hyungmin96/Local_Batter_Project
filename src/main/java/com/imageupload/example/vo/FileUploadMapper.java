package com.imageupload.example.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FileUploadMapper {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String file_name;
    private byte[] file_byte;
    private long file_size;

}
