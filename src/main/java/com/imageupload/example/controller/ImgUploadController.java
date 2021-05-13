package com.imageupload.example.controller;

import java.util.HashMap;
import java.util.Map;

import com.imageupload.example.service.ImgUploadservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImgUploadController {

    @Autowired
    private ImgUploadservice uploadService;

    @RequestMapping("/upload")
    public Map<String, Object> uploadFiles(MultipartFile[] uploadFiles) {
    
        Map<String, Object> resultMap = new HashMap<String, Object>();

        boolean fileUpload = uploadService.uploadFile(uploadFiles);

        if(fileUpload){
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "failed");
        }

        return resultMap;

    }

}
