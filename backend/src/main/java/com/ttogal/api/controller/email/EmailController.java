package com.ttogal.api.controller.email;

import com.ttogal.api.service.EmailService;
import com.ttogal.domain.email.dto.request.EmailRequest;
import com.ttogal.domain.email.dto.request.VerifyCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    @ResponseBody
    @PostMapping("/send")
    public String emailCheck(@RequestBody EmailRequest request) {
        return emailService.sendSimpleMessage(request.getEmail());
    }

    // 인증코드 인증
    @PostMapping("/verify")
    public boolean verify(@RequestBody VerifyCodeRequest request) {
        return emailService.verifyEmailCode(request.getEmail(), request.getAuthCode(), 300);
    }


}
