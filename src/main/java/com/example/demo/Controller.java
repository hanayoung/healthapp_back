package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public ImgVo insertImg(ImgVo imgVo){ // List로 바꿔야함
        MultipartFile file = imgVo.getFile();
        System.out.println("file "+file);
        System.out.println("fileName "+file.getName());
        String user = imgVo.getUser();
        System.out.println("user "+user);
        String info = imgVo.getInfo();
        System.out.println("info "+info);
        String fileName = file.getOriginalFilename();
        if(fileName.contains("'")){
            System.out.println("there is ' in fileName");
        }
        fileName = fileName.replaceAll("\\\"","");
        String result = UploadFile.uploadFile(file,fileName,user);
        if(result.equals("Success")){
            user = user.replaceAll("\\\"","");
            info = info.replaceAll("\\\"","");
            repository.saveImg(fileName,user,info);
        }
        return imgVo;
    }

    @GetMapping(
            value = "/image/download/{user}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getItemImageByName(@PathVariable String user) {
        try {
            HttpHeaders header = new HttpHeaders();
            FileSystemResource resource = null;
            List<ImgVo> list = repository.getImgData(user);
            String path = "/data/upload";
            if(list!=null){
                String fileName = list.get(0).getFileName();
                resource = new FileSystemResource(path+"/"+user+"/"+fileName);
                if (!resource.exists()) {
                    throw new RuntimeException();
                }
                Path filePath = null;
                filePath = Paths.get(path+"/"+user+"/"+fileName);
                header.add("Content-Type", Files.probeContentType(filePath));
                return new ResponseEntity<>(resource, header, HttpStatus.OK);
            }
            else{
                System.out.println("list null  ");
                throw new RuntimeException();
            }
        }catch (Exception e){
            System.out.println("exception "+e.getLocalizedMessage());
            throw new RuntimeException();
        }
    }
}
