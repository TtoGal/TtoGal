package com.ttogal.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;


//    @Value("${mail.properties.mail.smtp.starttls.enable}")
//    private boolean starttlsEnable;

//    @Value("${mail.properties.mail.smtp.starttls.required}")
//    private boolean starttlsRequired;

    @Value("${mail.properties.mail.smtp.connectiontimeout}")
    private int connectionTimeout;

    @Value("${mail.properties.mail.smtp.timeout}")
    private int timeout;

    @Value("${mail.properties.mail.smtp.writetimeout}")
    private int writeTimeout;



    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setJavaMailProperties(getMailProperties());

        Properties props = mailSender.getJavaMailProperties();
               return mailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true"); // SSL 활성화
        properties.put("mail.smtp.connectiontimeout", connectionTimeout);
        properties.put("mail.smtp.timeout", timeout);
        properties.put("mail.smtp.writetimeout", writeTimeout);
        properties.put("mail.debug", "true");

        return properties;
    }
}
