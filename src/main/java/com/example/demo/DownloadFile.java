package com.example.demo;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadFile {
    static public FileSystemResource downloadFile(){
        try {
            String path = "/data/upload";
            String fileName = "bfdd7ab5-ceb0-4c17-8ed3-161beabe0875_download.jpeg";
            FileSystemResource resource = new FileSystemResource(path+"/"+fileName);
            if (!resource.exists()) {
                throw new RuntimeException();
            }



            return resource;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
