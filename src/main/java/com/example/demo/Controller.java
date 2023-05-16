package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        System.out.println("user "+user);
        String info = imgVo.getInfo();
        System.out.println("info "+info);
        String fileName = file.getOriginalFilename();
        String result = UploadFile.uploadFile(file,fileName,user);

        if(result.equals("Success")){
            repository.saveImg(fileName,user,info);
        }
    }

    @GetMapping(
            value = "/image/download",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getItemImageByName(String userName) {
        HttpHeaders header = new HttpHeaders();
        FileSystemResource resource = null;
        try {
            List<ImgVo> list = repository.getImgData(userName);
            String path = "/data/upload";
            if(list!=null){
                String fileName = list.get(0).getFileName();
                resource = new FileSystemResource(path+"/"+userName+"/"+fileName);
                if (!resource.exists()) {
                    throw new RuntimeException();
                }
                Path filePath = null;
                filePath = Paths.get(path+"/"+userName+"/"+fileName);
                header.add("Content-Type", Files.probeContentType(filePath));
            }
        }catch (Exception e){
            System.out.println("exception "+e.getLocalizedMessage());
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

}
