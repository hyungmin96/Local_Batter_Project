package com.project.localbatter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateFileDTO {

    private String name;
    private String path;
    private String extention;
    private int fileSize;
    private String fileName;

}
