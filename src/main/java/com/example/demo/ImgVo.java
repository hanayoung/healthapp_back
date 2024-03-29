package com.example.demo;

import org.springframework.web.multipart.MultipartFile;

public class ImgVo {

    private MultipartFile file;

    private String fileName;
    private String user;

    private String info;

    public String getInfo() {
        return info;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUser() {
        return user;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "ImgVo{" +
                "file=" + file +
                ", fileName='" + fileName + '\'' +
                ", user='" + user + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
