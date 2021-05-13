package com.imageupload.example.service;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.imageupload.example.japrepositorys.imgJpaRepsitory;
import com.imageupload.example.vo.FileVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImgUploadservice {
    
    @Autowired
    private imgJpaRepsitory ImgJpaRepsitory;

    private final String path = "D:\\ImageUpload example\\src\\main\\downloads\\";

    private String generateFileName(MultipartFile file){
        Calendar car = Calendar.getInstance();
        Date date = car.getTime();
        String fileName = new SimpleDateFormat("yyyyMMdd").format(date) +
                            "_" + file.getOriginalFilename();
        return fileName;
    }

    @Transactional
    public boolean uploadFile(MultipartFile[] files){
        
        Map<String, Object> fileMap = new HashMap<String, Object>();

        for(MultipartFile file : files){

            try{
                String filename = generateFileName(file);
                File tempFile = new File(path + filename);

                fileMap.put("fileName", filename);
                fileMap.put("fileSize", file.getSize());
                System.out.println("hashmap : " + fileMap);
                //file.transferTo(tempFile);
                FileVo mapper = 
                    FileVo.builder()
                    .file_byte(file.getBytes())
                    .file_name(filename)
                    .file_size(file.getSize())
                    .build();

             //   ImgJpaRepsitory.save(mapper);
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    

}
