package org.example.crm_project.modules.products_managerment.presentation.controller;

import org.example.crm_project.modules.products_managerment.presentation.dto.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {

    @PostMapping
    public ApiResponse<String> uploadImage(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File tải lên không được để trống");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Chỉ cho phép tải lên file định dạng ảnh");
        }

        try {
            String originalFilename = file.getOriginalFilename();

            String fileName = originalFilename != null ? originalFilename : "unknow";
            String extension = "";
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                extension = fileName.substring(dotIndex);
                fileName = fileName.substring(0, dotIndex);
            }

            String timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String newFileName = timeStamp + "_" + fileName + extension;

            Path path = Paths.get("uploads/" + newFileName);

            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            return ApiResponse.success("Upload image success", "/uploads/" + newFileName);
        } catch (IOException ex) {
            throw new RuntimeException("Lỗi khi lưu file: " + ex.getMessage());
        }
    }
}
