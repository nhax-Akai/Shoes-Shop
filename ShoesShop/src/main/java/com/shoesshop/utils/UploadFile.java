package com.shoesshop.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadFile {
    public static void uploadFiles(MultipartFile multipartFile, String filename) throws IOException {
        Path uploadPath = Paths.get("uploads/");
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("Could not upload file "+ filename);
        }
    }
}
