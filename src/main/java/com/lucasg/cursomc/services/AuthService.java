package com.lucasg.cursomc.services;

import com.lucasg.cursomc.domain.Cliente;
import com.lucasg.cursomc.repositories.ClienteRepository;
import com.lucasg.cursomc.services.exceptions.ObjectNotFoundExeception;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder pe;
    private final EmailService emailService;

    private Random random = new Random();


    public AuthService(ClienteRepository clienteRepository, BCryptPasswordEncoder pe, EmailService emailService) {
        this.clienteRepository = clienteRepository;
        this.pe = pe;
        this.emailService = emailService;
    }

    public void sendNewPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente == null) {
            throw new ObjectNotFoundExeception("Email não cadastrado");
        }

        String newPass = newPassword();
        cliente.setSenha(pe.encode(newPass));

        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        if (opt == 0) {
            return (char) (random.nextInt(10) + 48);
        } else if (opt == 1) {
            return (char) (random.nextInt(26) + 65);
        } else {
            return (char) (random.nextInt(26) + 97);
        }
    }


}
