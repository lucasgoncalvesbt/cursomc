package com.lucasg.cursomc.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;

@Log4j2
public class MockEmailService extends AbstractEmailService {

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Simulando envio de email...");
        log.info(msg.toString());
        log.info("Email enviado");
    }
}
