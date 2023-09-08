package com.example.demo.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileHandlingService {
    @Value("${file.path}")
    private String base;

    @Transactional
    public String save(MultipartFile multipartFile) throws IOException {
        String type = multipartFile.getContentType().split("/")[1];
        String name = UUID.randomUUID() + "." + type;

        Path filePath = Paths.get(base, name);
        Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return name;
    }

    public FileSystemResource loadData(String path) {
        FileSystemResource fileResource = new FileSystemResource(base + "/" + path);
        return fileResource;
    }
}
