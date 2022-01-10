package com.lucasg.cursomc.services.validation;

import com.lucasg.cursomc.controllers.exceptions.FieldMessage;
import com.lucasg.cursomc.domain.Cliente;
import com.lucasg.cursomc.domain.enums.TipoCliente;
import com.lucasg.cursomc.dto.ClienteDTO;
import com.lucasg.cursomc.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lucasg.cursomc.services.validation.utils.BR.isValidCNPJ;
import static com.lucasg.cursomc.services.validation.utils.BR.isValidCPF;

@RequiredArgsConstructor
public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClienteDTO> {

    private final ClienteRepository clienteRepository;
    private final HttpServletRequest request;

    @Override
    public void initialize(ClientUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));
        List<FieldMessage> list = new ArrayList<>();

        Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
        if (cliente != null && !cliente.getId().equals(uriId)) {
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