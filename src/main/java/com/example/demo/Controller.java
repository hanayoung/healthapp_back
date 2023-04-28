package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
