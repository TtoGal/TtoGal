package com.ttogal.api.controller.email;

import com.ttogal.api.service.email.EmailService;
import com.ttogal.api.controller.email.dto.request.EmailRequestDto;
import com.ttogal.api.controller.email.dto.request.VerifyCodeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> emailCheck(@RequestBody EmailRequestDto request) {
        String simpleMessage=emailService.sendSimpleMessage(request.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(simpleMessage);
    }

    // 인증코드 인증
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verify(@RequestBody VerifyCodeRequestDto request) {
        Boolean isVerified=emailService.verifyEmailCode(request.getEmail(), request.getAuthCode());
        return ResponseEntity.status(HttpStatus.OK)
                .body(isVerified);
    }


}
