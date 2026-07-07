package com.ygor.helpdesk.services;

import java.util.List;
import java.util.Optional;

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

    public TecnicoDTO findById(Integer id) {
    	return new TecnicoDTO(buscarEntidade(id));
  }
    
    public List<TecnicoDTO> findAll() {
		return repository.findAll().stream()
				.map(TecnicoDTO::new)
				.toList();
    }
     
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return repository.save(newObj);
    }

    
    public TecnicoDTO update(Integer id, TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = buscarEntidade(id);

        validaPorCpfEEmail(objDTO);

        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setEmail(objDTO.getEmail());
        
        if (objDTO.getSenha() != null && !objDTO.getSenha().isBlank()
                && !encoder.matches(objDTO.getSenha(), oldObj.getSenha())) {
            oldObj.setSenha(encoder.encode(objDTO.getSenha()));
        }

        return new TecnicoDTO(repository.save(oldObj));
    }


    public void delete(Integer id) {
        Tecnico obj = buscarEntidade(id);
        if (!obj.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("O Técnico possui ordens de serviço e não pode ser deletado!");
        }
        repository.deleteById(id);
    }
    

    public Tecnico buscarEntidade(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
    }


	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && !obj.get().getId().equals(objDTO.getId())) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && !obj.get().getId().equals(objDTO.getId())) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}
}

