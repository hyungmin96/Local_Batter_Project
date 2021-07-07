package com.imageupload.example.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BuyingDTO {
    
    private String title;
    private String description;
    private String date;
    private String price;
    private String roomTitle;
    private String limit;
    private MultipartFile[] files;
}
