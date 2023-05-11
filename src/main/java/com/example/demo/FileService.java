package com.example.demo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FileService {

	//nowon
	public final String CHARTIMGPATH ="/usr/local/tomcat8/webapps/GMC_appraisal/resources/hwpChartImg/"; // 한글용 차트 이미지

	public Map<String, String> fileUpload(MultipartFile uploadFile, String key, String path) throws IllegalStateException, IOException{
		return upload(uploadFile, path, key);
	}

	//업로드
	public Map<String, String> upload(MultipartFile upLoadFile, String path, String key) throws IllegalStateException, IOException{
		File dir = new File(path);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		long fileSize = upLoadFile.getSize();

		String readFilePath = "";
		if (fileSize > 0) {
			//업로드 경로
			readFilePath = path + "/" + key + "/" + upLoadFile.getOriginalFilename();

			//폴더 경로 없을 경우 폴더 만들기
			File targetDir = new File(path + "/" + key);
			if(!targetDir.exists()){
				targetDir.mkdirs();
			}

			//업로드
			FileCopyUtils.copy(upLoadFile.getBytes(), new FileOutputStream(new File(readFilePath)));

			Map<String, String> result = new HashMap<String, String>();
			result.put("fileName", upLoadFile.getOriginalFilename());
			result.put("filePath", readFilePath);
			return result;
		}

		return null;
	}

	//업로드
	public Map<String, String> companyUpload(MultipartFile upLoadFile, String path, String uuid) throws IllegalStateException, IOException{
		File dir = new File(path);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		long fileSize = upLoadFile.getSize();

		String readFilePath = "";
		if (fileSize > 0) {
			//업로드 경로
			readFilePath = path + "/" + uuid + "/" + upLoadFile.getOriginalFilename();

			//폴더 경로 없을 경우 폴더 만들기
			File targetDir = new File(path + "/" + uuid);
			if(!targetDir.exists()){
				targetDir.mkdirs();
			}

			//업로드
			FileCopyUtils.copy(upLoadFile.getBytes(), new FileOutputStream(new File(readFilePath)));

			Map<String, String> result = new HashMap<String, String>();
			result.put("fileName", upLoadFile.getOriginalFilename());
			result.put("filePath", readFilePath);
			return result;
		}

		return null;
	}
	
	//reflection용 업로드
		public String reflection_upload(MultipartFile upLoadFile, String path, String key) throws IllegalStateException, IOException{
			File dir = new File(path);

			if (!dir.exists()) {
				dir.mkdirs();
			}

			long fileSize = upLoadFile.getSize();

			String readFilePath = "";
			if (fileSize > 0) {
				//업로드 경로
				readFilePath = path + "/" + key + "/" + upLoadFile.getOriginalFilename();

				//폴더 경로 없을 경우 폴더 만들기
				File targetDir = new File(path + "/" + key);
				if(!targetDir.exists()){
					targetDir.mkdirs();
				}

				//업로드
				FileCopyUtils.copy(upLoadFile.getBytes(), new FileOutputStream(new File(readFilePath)));

				return readFilePath;
			}

			return "";
		}
		
	//삭제
	public void deleteFile(String path){
		File file = new File(path);
		if(file.exists())
			file.delete();
	}

	public String chartImgSave(String chartCode) throws IOException {
		String chartImgName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
		if(chartCode != null && chartCode.contains(",")){
			String data = chartCode.split(",")[1];
			byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);
			BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));

			File dir = new File(CHARTIMGPATH);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			ImageIO.write(bufImg, "jpg", new File(CHARTIMGPATH + "/" + chartImgName));
			return chartImgName;
		}
		return "";
	}
}
