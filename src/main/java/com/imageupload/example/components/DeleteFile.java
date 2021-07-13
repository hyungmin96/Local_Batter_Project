package com.imageupload.example.components;

import java.io.File;

public class DeleteFile {

    private final String[] files;
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";

    public DeleteFile(String[] files){
        this.files = files;
    }

    public void deleteFile(){

        for(String file : files){
            if(!file.contains("default")){
                File f = new File(root + file);

                if(f.exists())
                    f.delete();
            }
        }
    }
}
