package com.lucasg.cursomc.services;

import com.lucasg.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendOrderConfirmationEmail(Pedido pedido);
    void sendEmail(SimpleMailMessage msg);
}
