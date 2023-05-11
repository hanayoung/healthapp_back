package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public void insertImg(@RequestParam("file") MultipartFile file,HttpServletRequest request){
        System.out.println("Connection from Android!!");
        System.out.println("File "+file);
        System.out.println("file "+file.getContentType());
        UploadFile.uploadFile(file,request);

//        repository.saveImg(file);
    }

}
