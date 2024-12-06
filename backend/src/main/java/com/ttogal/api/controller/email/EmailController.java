package com.ttogal.api.controller.email;

import com.ttogal.api.service.email.EmailService;
import com.ttogal.api.controller.email.dto.request.EmailRequestDto;
import com.ttogal.api.controller.email.dto.request.VerifyCodeRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    @Operation(
            summary = "이메일 인증 코드 발송",
            description = "사용자가 제공한 이메일 주소로 인증 코드를 전송합니다.",
            tags = { "Email" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 코드 전송 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 이메일 주소"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/send")
    public ResponseEntity<String> emailCheck(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "인증 코드를 보낼 이메일 주소",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmailRequestDto.class))
            ) EmailRequestDto request) {
        String simpleMessage = emailService.sendSimpleMessage(request.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(simpleMessage);
    }

    @Operation(
            summary = "인증 코드 검증",
            description = "사용자가 제공한 이메일과 인증 코드를 검증합니다.",
            tags = { "Email" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 코드 검증 성공",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 인증 코드 또는 이메일",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verify(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "이메일과 인증 코드를 포함하는 요청 본문",
                    required = true,
                    content = @Content(schema = @Schema(implementation = VerifyCodeRequestDto.class))
            ) VerifyCodeRequestDto request) {
        Boolean isVerified = emailService.verifyEmailCode(request.getEmail(), request.getAuthCode());
        return ResponseEntity.status(HttpStatus.OK).body(isVerified);
    }
}
