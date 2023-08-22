package com.example.demo.application;

import com.example.demo.dto.FileService;
import com.example.demo.entity.Attachment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final FileService fileService;

    @Transactional
    public String saveAttachment(MultipartFile multipartFile, OAuth2User principal) {
        String email = principal.getAttribute("email");
        try {
            String fileName = fileService.save(multipartFile);
            Attachment attachment = Attachment.builder()
                    .sender(email)
                    .fileName(fileName)
                    .build();
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FileSystemResource fetchAttachment(String fileName) {
        FileSystemResource fileSystemResource = fileService.loadData(fileName);
        return fileSystemResource;
    }
}
