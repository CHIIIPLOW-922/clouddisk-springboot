package com.chiiiplow.clouddisk.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class SendEmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendVerifyCodeToEmail() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    }
}
