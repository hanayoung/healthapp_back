package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UploadFile {
        private String uploadFilename;  // 작성자가 업로드한 파일명
        private String storeFilename;   // 서버 내부에서 관리하는 파일명

        public static void uploadFile(MultipartFile file){
                String uploadPath ="/Users/aoreo/Downloads";
                String originalName = file.getOriginalFilename();
                System.out.println(file.getContentType());
                System.out.println("isEmpty  "+file.isEmpty());
                String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);

                System.out.println("originalName  "+file.getOriginalFilename());
                System.out.println("fileName  "+fileName);
                String uuid = UUID.randomUUID().toString();

//                String saveFileName = uploadPath + File.separator + uuid + "_" + fileName;
                String saveFileName = uploadPath + File.separator + uuid + "_" + file.getOriginalFilename();
                System.out.println("saveFileName "+saveFileName);
                Path savePath = Paths.get(saveFileName);

                try{
//                        file.transferTo(savePath.toFile());
//                        Files.copy(file.getInputStream(), savePath);
                        FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(saveFileName));
                } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                }
        }
}
