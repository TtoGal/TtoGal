package com.ttogal.api.service.email;

import com.ttogal.common.exception.ExceptionCode;
import com.ttogal.common.exception.email.EmailException;
import com.ttogal.common.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${mail.sender-email}") // 프로퍼티에서 이메일 주입
    private String senderEmail;

    private final RedisUtil redisUtil;

    // 랜덤으로 숫자 생성
    public String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) { // 인증 코드 8자리
            int index = random.nextInt(3); // 0~2까지 랜덤, 랜덤값으로 switch문 실행

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97)); // 소문자
                case 1 -> key.append((char) (random.nextInt(26) + 65)); // 대문자
                case 2 -> key.append(random.nextInt(10)); // 숫자
            }
        }
        return key.toString();
    }

    public MimeMessage createMail(String mail, String number) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setSubject("이메일 인증");
        String body = "";
        body += "<h3>요청하신 인증 번호입니다.</h3>";
        body += "<h1>" + number + "</h1>";
        body += "<h3>감사합니다.</h3>";
        message.setText(body, "UTF-8", "html");

        return message;
    }


    // 메일 발송
    public String sendSimpleMessage(String sendEmail) {
        String number = createNumber(); // 랜덤 인증번호 생성

        try {

            MimeMessage message = createMail(sendEmail, number); // 메일 생성
            javaMailSender.send(message); // 메일 발송

            // Redis에 인증 코드 저장
            String key = redisUtil.generateKey(sendEmail);
            redisUtil.setDataExpire(key, number, 300); // 5분 동안 유효
            log.info("인증 코드 저장: Key={}, Code={}", key, number);

        } catch (MailException | MessagingException e) {
            throw new EmailException(ExceptionCode.EMAIL_SENDING_ERROR); // 사용자 정의 예외 던지기
        }

        return number; // 생성된 인증번호 반환
    }

    //메일 검증

    // 코드 검증
    public Boolean verifyEmailCode(String email, String code) {
        String key = redisUtil.generateKey(email);
        String storedCode = redisUtil.getData(key);

        log.info("Redis에서 조회된 코드: {}", storedCode);

        if (storedCode == null) {
            log.warn("Redis에 저장된 코드가 없습니다. Key={}", key);
            return false;
        }

        boolean isVerified = storedCode.equals(code);
        if (isVerified) {
            redisUtil.deleteData(email); // 인증 성공 후 Redis에서 코드 삭제
            log.info("인증 성공: Key={}, Code={}", key, code);
        } else {
            log.warn("인증 실패: Key={}, 입력된 Code={}, 저장된 Code={}", key, code, storedCode);
        }
        return isVerified;
    }
}
