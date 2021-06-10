package com.imageupload.example.components.boardServiceMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.imageupload.example.models.boardVo;
import com.imageupload.example.models.fileVo;

import org.springframework.web.multipart.MultipartFile;

public class generateFile {

    private MultipartFile[] files;
    private boardVo vo;

    private FileOutputStream fos;
    private final String root = "D:\\ImageUpload example\\src\\main\\downloads\\";
    private String tempName = "";
    private String extention;

    public generateFile(boardVo vo, MultipartFile[] files) {
        this.vo = vo;
        this.files = files;
    }

    public List<fileVo> generateFileVoList() {

        List<fileVo> fileInfos = new ArrayList<>();

        for (MultipartFile file : files) {

            try {

                extention = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

                tempName = UUID.randomUUID().toString();

                byte[] bytes = file.getBytes();

                fos = new FileOutputStream(new File(root + tempName + extention));

                fos.write(bytes);

                fos.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileVo filevo = fileVo.builder()
                            .tempName(tempName + extention)
                            .filePath(root + tempName + extention)
                            .originName(file.getOriginalFilename())
                            .fileSize(file.getSize()).board(vo).build();

            fileInfos.add(filevo);
        }

        return fileInfos;

    }

}
