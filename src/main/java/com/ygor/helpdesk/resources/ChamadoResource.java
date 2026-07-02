package com.ygor.helpdesk.resources;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ygor.helpdesk.domain.Chamado;
import com.ygor.helpdesk.domain.dtos.ChamadoDTO;
import com.ygor.helpdesk.services.ChamadoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/chamados")
@RequiredArgsConstructor
public class ChamadoResource {

	private final ChamadoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
		Chamado obj = service.findById(id);
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}

	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll() {
		List<ChamadoDTO> listDTO = service.findAll()
				.stream()
				.map(ChamadoDTO::new)
				.toList();
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
	public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO objDTO) {
		Chamado obj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDTO) {
		Chamado newObj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new ChamadoDTO(newObj));
	}
}
