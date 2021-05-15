package com.imageupload.example.methods.boardServiceMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.imageupload.example.Vo.fileVo;

import org.springframework.web.multipart.MultipartFile;

public class generateFile {

    private MultipartFile[] files;
    private FileOutputStream fos;
    private final String root = "D:\\ImageUpload example\\src\\main\\downloads\\";
    private String tempName = "";
    private String extention;

    public generateFile(MultipartFile[] files) {
        this.files = files;
    }

    public void createFile() {

        for (MultipartFile file : files) {

            try {

                this.extention = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

                this.tempName = UUID.randomUUID().toString();

                byte[] bytes = file.getBytes();

                fos = new FileOutputStream(new File(root + tempName + extention));

                fos.write(bytes);

                fos.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public List<fileVo> generateFileVoList() {

        List<fileVo> fileInfos = new ArrayList<>();

        for (MultipartFile file : files) {
            fileVo filevo = fileVo.builder()
                            .tempName(tempName)
                            .filePath(root + tempName + extention)
                            .originName(file.getOriginalFilename())
                            .fileSize(file.getSize()).build();

            fileInfos.add(filevo);
        }

        return fileInfos;

    }

}
