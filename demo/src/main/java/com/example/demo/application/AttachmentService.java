package com.example.demo.application;

import com.example.demo.entity.Attachment;
import com.example.demo.repository.AttachmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
public class AttachmentService {

    private final FileService fileService;

    private final AttachmentRepository attachmentRepository;

    @Transactional
    public String saveAttachment(MultipartFile multipartFile, OAuth2User principal) {
        String email = principal.getAttribute("email");
        try {
            String fileName = fileService.save(multipartFile);
            Attachment attachment = Attachment.builder()
                    .sender(email)
                    .fileName(fileName)
                    .build();
            attachmentRepository.save(attachment);
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

    @Service
    @RequiredArgsConstructor
    public static class FileService{
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
}
