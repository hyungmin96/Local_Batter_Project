package com.imageupload.example.imageupload.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class coupangDTO {
    
    private String title;
    private String descryption;
    private String price;
    private String writer;

}
