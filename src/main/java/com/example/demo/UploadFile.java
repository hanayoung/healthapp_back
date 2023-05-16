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
        /*      Map<String, String> result = new HashMap<>();
                MultipartFile file = request.getFile("test_file");
                FileService fs = new FileService();
                try {
                        result = fs.upload(file,"/data/upload", "test");
                        System.out.println("FILE_PATH" + result.get("filePath"));
                } catch (Exception e) {
                        e.printStackTrace();
                }*/

                System.out.println("@@@ IN @@@");

                // String rootPath = request.getServletContext().getRealPath("/upload");
                String uploadPath ="/data/upload/"+userName;
                File targetDir = new File(uploadPath);
                if(!targetDir.exists()){
                        targetDir.mkdirs();
                }

                System.out.println(file.getContentType());
                System.out.println("isEmpty  "+file.isEmpty());

                System.out.println("originalName  "+file.getOriginalFilename());

                // String saveFileName = rootPath + File.separator + uuid + "_" + fileName;
                // String saveFileName = uploadPath + File.separator + uuid + "_" + file.getOriginalFilename();
                String saveFileName = uploadPath + File.separator + fileName;
                System.out.println("fileName " +fileName);
                System.out.println("saveFileName "+saveFileName);
                Path savePath = Paths.get(saveFileName);

                try{
                        file.transferTo(savePath.toFile());
                        return "Success";
//                        Files.copy(file.getInputStream(), savePath);
//                        FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(saveFileName));
                        // fs.upload(file,"/data/upload", "test/");
                } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                }
        }
}