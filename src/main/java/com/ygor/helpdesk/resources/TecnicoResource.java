package com.ygor.helpdesk.resources;

import java.net.URI;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ygor.helpdesk.domain.Tecnico;
import com.ygor.helpdesk.domain.dtos.TecnicoDTO;
import com.ygor.helpdesk.domain.dtos.groups.CreateGroup;
import com.ygor.helpdesk.domain.dtos.groups.UpdateGroup;
import com.ygor.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
@RequiredArgsConstructor
public class TecnicoResource {

	private final TecnicoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<TecnicoDTO> listDTO = service.findAll();
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Validated(CreateGroup.class) @RequestBody TecnicoDTO objDTO) {
		Tecnico newObj = service.create(objDTO);  
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id,
	        @Validated(UpdateGroup.class) @RequestBody TecnicoDTO objDTO) {
	    return ResponseEntity.ok().body(service.update(id, objDTO));
	}


	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
