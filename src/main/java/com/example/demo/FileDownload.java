package com.example.demo;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

public class FileDownload extends AbstractView {
	public FileDownload() {
        setContentType("application/download; utf-8");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	File file = null;
        String name = "";
        if(model.get("file")==null){
            Map<String, Object> param = (Map<String, Object>) model.get("model");
            try{
                file = new File((String) param.get("file"));
            }catch (Exception e) {
                file = (File) param.get("file");
            }
            name = (String) param.get("name");
        }else{
            file = (File) model.get("file");
        }
        response.setContentType(getContentType());
        response.setContentLength((int) file.length());

        String fileName = null;
        if(name!=null && name!=""){
        	fileName=name;
        }else{
        	fileName = file.getName();
        }
        
        String header = request.getHeader("User-Agent");

        if (header.contains("MSIE") || header.contains("Trident")) {
            fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
           response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        }

        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");

        OutputStream out = response.getOutputStream();

        FileInputStream fis = null;

        try {

            fis = new FileInputStream(file);

            FileCopyUtils.copy(fis, out);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (fis != null) {

                try {
                    fis.close();
                } catch (Exception e) {
                }
            }

        }// try end;

        out.flush();
    }
}
