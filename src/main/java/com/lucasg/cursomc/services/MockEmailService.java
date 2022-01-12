package com.lucasg.cursomc.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

@Log4j2
public class MockEmailService extends AbstractEmailService {

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Simulando envio de email...");
        log.info(msg.toString());
        log.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        log.info("Simulando envio de email HTML...");
        log.info(msg.toString());
        log.info("Email enviado");
    }
}
