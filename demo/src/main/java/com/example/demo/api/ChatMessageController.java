package com.example.demo.api;

import com.example.demo.application.ChatMessageService;
import com.example.demo.application.FileHandlingService;
import com.example.demo.application.MemberService;
import com.example.demo.dto.chat.ChatMessageResponseDto;
import com.example.demo.exception.RequestNotAuthorizedException;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/chatmessages")
@RequiredArgsConstructor
public class ChatMessageController { // /chatmessages/getchatmessages/{destination}/{amount}
    private final ChatMessageService chatMessageService;

    private final FileHandlingService fileHandlingService;

    private final MemberService memberService;

    // /chatmessages/getchatmessages/9LrEwwuPdEhbnipLlWBY/3
    @GetMapping("/getchatmessages/{destination}/{amount}")
    @Operation(summary = "(destination)을 destination으로 하는 가장 최근 (amount)메시지를 가져옴", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<ChatMessageResponseDto>> getLatestChatMessages(@AuthenticationPrincipal OAuth2User principal, @PathVariable String destination, @PathVariable int amount) throws RequestNotAuthorizedException {
        List<ChatMessageResponseDto> chatMessages = chatMessageService.getLatestChatMessages(memberService.getMember(principal), destination, amount);
        return new ResponseEntity<>(chatMessages, HttpStatus.OK);
    }
// /chatmessages/uploadfile"
    @PostMapping("/uploadfile")
    @Operation(summary = "파일 업로드", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<String> uploadAttachmentForChat(@AuthenticationPrincipal OAuth2User principal, @RequestParam("attachment") MultipartFile attachment) throws IOException {
        String fileName = fileHandlingService.save(attachment);
        return ResponseEntity.ok(fileName);
    }
// /chatmessages/getfile
    @GetMapping("/getfile/{fileName}")
    @Operation(summary = "파일 이름으로 파일 가져오기", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<FileSystemResource> getAttachmentForChat(@PathVariable String fileName) {
        FileSystemResource fileSystemResource = fileHandlingService.loadData(fileName);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileSystemResource.getFilename())
                .body(fileSystemResource);
    }
}
