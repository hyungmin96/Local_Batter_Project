package com.imageupload.example.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.imageupload.example.entity.BuyingChatRoomEntity;
import com.imageupload.example.entity.BuyingFileEntity;
import org.springframework.web.multipart.MultipartFile;

public class GenerateBuyingFiles {

    private MultipartFile[] files;
    private BuyingChatRoomEntity buyingChatRoomEntity;

    private FileOutputStream fos;
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";
    private String tempName = "";
    private String extention;

    public GenerateBuyingFiles(BuyingChatRoomEntity buyingChatRoomEntity, MultipartFile[] files) {
        this.files = files;
        this.buyingChatRoomEntity = buyingChatRoomEntity;
    }

    public List<BuyingFileEntity> generateFileVoList() {

        List<BuyingFileEntity> fileInfos = new ArrayList<>();

        for (MultipartFile file : files) {

            try {

                extention = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

                tempName = UUID.randomUUID().toString();

                byte[] bytes = file.getBytes();

                fos = new FileOutputStream(new File(root + tempName + extention));

                fos.write(bytes);

                fos.close();

                BuyingFileEntity buyingFileEntity = BuyingFileEntity.builder()
                .name(tempName + extention)
                .path(root + tempName + extention)
                .buyingChatRoomEntity(buyingChatRoomEntity).build();

                fileInfos.add(buyingFileEntity);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileInfos;

    }

}
