package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RequestMapping("api")
@RestController
public class Controller {

    @Autowired
    private Repository repository;

    @GetMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping(value="/insert")
    public HrVo insertData(@RequestBody HrVo hrVo) {
        System.out.println("Connection from Android!");
        System.out.println(hrVo.getValue());
        repository.saveData(hrVo);
        return hrVo;
    }

    // 모든 hr정보 가져오는 get method 작성
    @GetMapping(value="/get")
    public List<HrVo> getData(){
        List<HrVo> data = repository.getData();
        return data;
    }

    @PostMapping(value = "/image/upload")
    public void insertImg(ImgVo imgVo){
        MultipartFile file = imgVo.getFile();
        System.out.println("file "+file);
        String user = imgVo.getUser();
        String info = imgVo.getInfo();
        String fileName = file.getOriginalFilename();
        String result = UploadFile.uploadFile(file,fileName);

        if(result.equals("Success")){
            repository.saveImg(fileName,user,info);
        }
    }

    @GetMapping(
            value = "/image/download",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getItemImageByName() {
        try {
            String path = "/data/upload";
            String fileName = "bfdd7ab5-ceb0-4c17-8ed3-161beabe0875_download.jpeg";
            FileSystemResource resource = new FileSystemResource(path+"/"+fileName);
            if (!resource.exists()) {
                throw new RuntimeException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(path+fileName);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
