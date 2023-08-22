package com.example.demo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class MemberController {
    @GetMapping("/")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
}