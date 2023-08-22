package com.example.demo.api;

import com.example.demo.application.AttachmentService;
import com.example.demo.application.ChatMessageService;
import com.example.demo.dto.ChatMessageDto;
import com.example.demo.exception.RequestNotAuthorizedException;
import com.example.demo.exception.WrongMemberEntryException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/chatmessages")
@RequiredArgsConstructor
public class ChatMessageController { // /chatmessages/getchatmessages/{destination}/{amount}
    private final AttachmentService attachmentService;
    private final ChatMessageService chatMessageService;
    @GetMapping("/getchatmessages/{destination}/{amount}")
    @Operation(summary = "(destination)을 destination으로 하는 가장 최근 (amount)메시지를 가져옴", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<ChatMessageDto>> getLatestChatMessages(@AuthenticationPrincipal OAuth2User principal, @PathVariable String destination, @PathVariable int amount) throws WrongMemberEntryException, RequestNotAuthorizedException {
        String email = principal.getAttribute("email");
        // check if principal is authorized to access messages
        List<ChatMessageDto> chatMessages = chatMessageService.getLatestChatMessages(email, destination, amount);
        return new ResponseEntity<>(chatMessages, HttpStatus.OK);
    }
// /chatmessages/uploadfile"
    @PostMapping("/uploadfile")
    public ResponseEntity<String> uploadAttachmentForChat(@AuthenticationPrincipal OAuth2User principal, @RequestParam("attachment") MultipartFile attachment) {
        System.out.println("uploading...");
        String fileName = attachmentService.saveAttachment(attachment, principal);
        return ResponseEntity.ok(fileName);
    }
// /chatmessages/getfile
    @GetMapping("/getfile/{fileName}")
    public ResponseEntity<FileSystemResource> getAttachmentForChat(@AuthenticationPrincipal OAuth2User principal, @PathVariable String fileName) {
        FileSystemResource fileSystemResource = attachmentService.fetchAttachment(fileName);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileSystemResource.getFilename())
                .body(fileSystemResource);
    }
}
