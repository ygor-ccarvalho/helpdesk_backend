package com.ygor.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ygor.helpdesk.domain.Chamado;
import com.ygor.helpdesk.domain.Cliente;
import com.ygor.helpdesk.domain.Tecnico;
import com.ygor.helpdesk.domain.dtos.ChamadoDTO;
import com.ygor.helpdesk.domain.enums.Prioridade;
import com.ygor.helpdesk.domain.enums.Status;
import com.ygor.helpdesk.repositories.ChamadoRepository;
import com.ygor.helpdesk.services.exceptions.ObjectnotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChamadoService {

	private final ChamadoRepository repository;
	private final TecnicoService tecnicoService;
	private final ClienteService clienteService;

	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}

	public Chamado create(ChamadoDTO objDTO) {
		return repository.save(newChamado(objDTO));
	}

	public Chamado update(Integer id, ChamadoDTO objDTO) {
		objDTO.setId(id);
		findById(id);
		return repository.save(newChamado(objDTO));
	}

	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());

		Chamado chamado = new Chamado();
		if (obj.getId() != null) {
			chamado.setId(obj.getId());
		}

		if (obj.getStatus().equals(Status.ENCERRADO.getCodigo())) {
			chamado.setDataFechamento(LocalDate.now());
		}

		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());

		return chamado;
	}
}
