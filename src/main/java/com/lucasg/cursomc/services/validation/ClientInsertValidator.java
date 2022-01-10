package com.lucasg.cursomc.services.validation;

import com.lucasg.cursomc.controllers.exceptions.FieldMessage;
import com.lucasg.cursomc.domain.Cliente;
import com.lucasg.cursomc.domain.enums.TipoCliente;
import com.lucasg.cursomc.dto.ClienteNewDTO;
import com.lucasg.cursomc.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.lucasg.cursomc.services.validation.utils.BR.isValidCNPJ;
import static com.lucasg.cursomc.services.validation.utils.BR.isValidCPF;

@RequiredArgsConstructor
public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClienteNewDTO> {

    private final ClienteRepository clienteRepository;

    @Override
    public void initialize(ClientInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();


        if(objDto.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCod()) && !isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if(objDto.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
        if(cliente != null){
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}