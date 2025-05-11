package com.example.my_project.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            String uploadDir = "src/main/resources/static/uploads/";
            String targetLocation = uploadDir + fileName;
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path path = Paths.get(targetLocation);

            Files.write(path, bytes);

            return "File uploaded successfully";
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }

    @DeleteMapping("/remove/{fileName}")
    public String removeFile(@PathVariable String fileName) {
        String uploadDir = "src/main/resources/static/uploads/";
        Path uploadPath = Paths.get(uploadDir + fileName);
        if (Files.exists(uploadPath)) {
            try {
                Files.delete(uploadPath);
                return "File remove successfully";
            } catch (IOException e) {
                return "Failed to remove file: " + e.getMessage();
            }
        }
        return "File not found";
    }
}
