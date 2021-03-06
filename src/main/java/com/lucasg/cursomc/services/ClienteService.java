package com.lucasg.cursomc.services;

import com.lucasg.cursomc.domain.Cidade;
import com.lucasg.cursomc.domain.Cliente;
import com.lucasg.cursomc.domain.Endereco;
import com.lucasg.cursomc.domain.enums.Perfil;
import com.lucasg.cursomc.domain.enums.TipoCliente;
import com.lucasg.cursomc.dto.ClienteDTO;
import com.lucasg.cursomc.dto.ClienteNewDTO;
import com.lucasg.cursomc.repositories.ClienteRepository;
import com.lucasg.cursomc.repositories.EnderecoRepository;
import com.lucasg.cursomc.security.UserSS;
import com.lucasg.cursomc.services.exceptions.AuthorizationException;
import com.lucasg.cursomc.services.exceptions.DataIntegrityExeception;
import com.lucasg.cursomc.services.exceptions.ObjectNotFoundExeception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final S3Service s3Service;
    private final ImageService imageService;

    public Cliente find(Integer id) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        return clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundExeception("Objeto n??o encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente findByEmail(String email) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso Negado");
        }

        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente == null) {
            throw new ObjectNotFoundExeception("Objeto n??o encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
        }

        return cliente;
    }

    @Transactional
    public Cliente create(Cliente cliente) {
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        Cliente clienteToUpdate = find(cliente.getId());
        updateData(clienteToUpdate, cliente);
        return clienteRepository.save(clienteToUpdate);
    }

    public void delete(Integer id) {
        find(id);

        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityExeception("N??o ?? poss??vel excluir um cliente porque  h?? entidades relacionadas");
        }
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {

        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + user.getId() + ".jpg";
        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

    }

    public Cliente fromDTO(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO clienteDTO) {
        Cliente cli = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteDTO.getTipoCliente()), bCryptPasswordEncoder.encode(clienteDTO.getSenha()));
        Cidade cid = new Cidade(clienteDTO.getCidadeId(), null, null);
        Endereco end = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(clienteDTO.getTelefone1());
        if (clienteDTO.getTelefone2() != null) {
            cli.getTelefones().add(clienteDTO.getTelefone2());
        }
        if (clienteDTO.getTelefone3() != null) {
            cli.getTelefones().add(clienteDTO.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente clienteToUpdate, Cliente cliente) {
        clienteToUpdate.setNome(cliente.getNome());
        clienteToUpdate.setEmail(cliente.getEmail());
    }
}
