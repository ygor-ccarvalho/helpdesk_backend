package com.ygor.helpdesk.resources;

import java.net.URI;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ygor.helpdesk.domain.Cliente;
import com.ygor.helpdesk.domain.dtos.ClienteDTO;
import com.ygor.helpdesk.domain.dtos.groups.CreateGroup;
import com.ygor.helpdesk.domain.dtos.groups.UpdateGroup;
import com.ygor.helpdesk.services.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/clientes")
@RequiredArgsConstructor
public class ClienteResource {

	private final ClienteService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		ClienteDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> listDTO = service.findAll();
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Validated(CreateGroup.class) @RequestBody ClienteDTO objDTO) {
		Cliente newObj = service.create(objDTO);  
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id,
			@Validated(UpdateGroup.class) @RequestBody ClienteDTO objDTO) {
		return ResponseEntity.ok().body(service.update(id, objDTO));   
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
