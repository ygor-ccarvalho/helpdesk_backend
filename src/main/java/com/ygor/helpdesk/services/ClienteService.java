package com.ygor.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ygor.helpdesk.domain.Pessoa;
import com.ygor.helpdesk.domain.Cliente;
import com.ygor.helpdesk.domain.dtos.ClienteDTO;
import com.ygor.helpdesk.repositories.PessoaRepository;
import com.ygor.helpdesk.repositories.ClienteRepository;
import com.ygor.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.ygor.helpdesk.services.exceptions.ObjectnotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository repository;
	private final PessoaRepository pessoaRepository;
	private final BCryptPasswordEncoder encoder;

	public ClienteDTO findById(Integer id) {
		return new ClienteDTO(buscarEntidade(id));
	}

	public List<ClienteDTO> findAll() {
		return repository.findAll().stream()
				.map(ClienteDTO::new)
				.toList();
	}

	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return repository.save(newObj);
	}

	public ClienteDTO update(Integer id, ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = buscarEntidade(id);

		validaPorCpfEEmail(objDTO);

        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setEmail(objDTO.getEmail());
        
        if (objDTO.getSenha() != null && !objDTO.getSenha().isBlank()
                && !encoder.matches(objDTO.getSenha(), oldObj.getSenha())) {
            oldObj.setSenha(encoder.encode(objDTO.getSenha()));
        }

        return new ClienteDTO(repository.save(oldObj));
    }

	public void delete(Integer id) {
		Cliente obj = buscarEntidade(id);
		if (!obj.getChamados().isEmpty()) {
			throw new DataIntegrityViolationException("O Cliente possui ordens de serviço e não pode ser deletado!");
		}
		repository.deleteById(id);
	}

	public Cliente buscarEntidade(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	private void validaPorCpfEEmail(ClienteDTO objDTO) {
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
