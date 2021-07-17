package com.imageupload.example.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.imageupload.example.dto.GenerateFileDTO;
import org.springframework.web.multipart.MultipartFile;

public class GenerateFile {

    private final MultipartFile[] files;

    private FileOutputStream fos;
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";
    private String tempName = "";
    private String extention;

    public GenerateFile(MultipartFile[] files) {
        this.files = files;
    }

    public List<GenerateFileDTO> createFile() {

        List<GenerateFileDTO> fileList = new ArrayList<>();

        for (MultipartFile file : files) {

            try {

                extention = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

                tempName = UUID.randomUUID().toString();

                byte[] bytes = file.getBytes();

                String filePath = root + tempName + extention;

                fos = new FileOutputStream(new File(filePath));

                fos.write(bytes);

                GenerateFileDTO generateFileDTO = new GenerateFileDTO();
                generateFileDTO.setFileSize(bytes.length);
                generateFileDTO.setExtention(extention);
                generateFileDTO.setFileName(tempName + extention);
                generateFileDTO.setPath(filePath);

                fileList.add(generateFileDTO);

                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return fileList;

    }

}
