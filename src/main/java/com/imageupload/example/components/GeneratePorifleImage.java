package com.imageupload.example.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class GeneratePorifleImage {
    
    private MultipartFile file;
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";

    public GeneratePorifleImage(MultipartFile file){
        this.file = file;
    }

    public String generateFile() throws IOException{
        
        String extention = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

        String tempName = UUID.randomUUID().toString();

        String filePath = root + tempName + extention;

        byte[] fileBytes = file.getBytes();

        File file = new File(filePath);

        FileOutputStream fos = new FileOutputStream(file);

        fos.write(fileBytes);

        fos.close();

        return tempName + extention;
    }

}
