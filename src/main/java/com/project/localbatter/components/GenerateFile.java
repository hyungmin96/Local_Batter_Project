package com.project.localbatter.components;

import com.project.localbatter.dto.GenerateFileDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class GenerateFile {

    private FileOutputStream fos;
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";
    private String tempName = "";
    private String extention;

    public List<GenerateFileDTO> createFile(MultipartFile[] files) {

        List<GenerateFileDTO> fileList = new ArrayList<>();

        if(files != null && files.length > 0) {

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
                    generateFileDTO.setName(tempName + extention);
                    generateFileDTO.setPath(filePath);

                    fileList.add(generateFileDTO);

                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
            return fileList;
    }
}
