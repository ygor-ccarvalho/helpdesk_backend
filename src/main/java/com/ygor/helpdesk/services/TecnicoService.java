package com.ygor.helpdesk.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ygor.helpdesk.domain.Pessoa;
import com.ygor.helpdesk.domain.Tecnico;
import com.ygor.helpdesk.domain.dtos.TecnicoDTO;
import com.ygor.helpdesk.repositories.PessoaRepository;
import com.ygor.helpdesk.repositories.TecnicoRepository;
import com.ygor.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.ygor.helpdesk.services.exceptions.ObjectnotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TecnicoService {

    private final TecnicoRepository repository;
    private final PessoaRepository pessoaRepository;
    private final BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return repository.save(newObj);
    }

    public Tecnico update(Integer id, TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);

        if (!encoder.matches(objDTO.getSenha(), oldObj.getSenha())) {
            objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        } else {
            objDTO.setSenha(oldObj.getSenha());
        }

        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return repository.save(newObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (!obj.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("O Técnico possui ordens de serviço e não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        pessoaRepository.findByCpf(objDTO.getCpf())
                .filter(p -> !p.getId().equals(objDTO.getId()))
                .ifPresent(p -> {
                    throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
                });

        pessoaRepository.findByEmail(objDTO.getEmail())
                .filter(p -> !p.getId().equals(objDTO.getId()))
                .ifPresent(p -> {
                    throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
                });
    }
}
