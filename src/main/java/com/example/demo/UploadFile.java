package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
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
        public static String uploadFile(MultipartFile file,String fileName,String userName){
                userName = userName.replaceAll("\\\"","");
                String uploadPath ="/data/upload/"+userName;
                File targetDir = new File(uploadPath);
                if(!targetDir.exists()){
                        targetDir.mkdirs();
                }
                System.out.println(file.getContentType());
                String saveFileName = uploadPath + File.separator + fileName;
                Path savePath = Paths.get(saveFileName);
                try{
                        file.transferTo(savePath.toFile());
                        return "Success";
                } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                }
        }
}