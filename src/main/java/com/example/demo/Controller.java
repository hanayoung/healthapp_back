package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
    public void insertImg(MultipartFile file){
/*      Map<String, String> result = new HashMap<>();
        System.out.println("Connection from Android!!");
        MultipartFile file = request.getFile("test_file");*/
       /* System.out.println("File "+file);
        System.out.println("file "+file.getContentType());
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + file.getOriginalFilename();
*/
        String fileName = file.getOriginalFilename();
        String result = UploadFile.uploadFile(file,fileName);
        if(result=="Success"){
            repository.saveImg(fileName);
        }
        /*FileService fs = new FileService();
                try {
                        result = fs.upload(file,"/data/upload", "test");
                        System.out.println("01: FilePath :: " + result.get("filePath"));
                } catch (Exception e) {
                        e.printStackTrace();
                                        }*/
//        repository.saveImg(file);
    }


    @GetMapping(
            value = "/image/download",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType() throws IOException{
        InputStream in = getClass().getResourceAsStream("/data/upload/bfdd7ab5-ceb0-4c17-8ed3-161beabe0875_download.jpeg");
        return IOUtils.toByteArray(in);
    }

}
