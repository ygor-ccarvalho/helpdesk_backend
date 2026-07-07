package com.ygor.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ygor.helpdesk.domain.Cliente;
import com.ygor.helpdesk.domain.dtos.groups.CreateGroup;
import com.ygor.helpdesk.domain.dtos.groups.UpdateGroup;
import com.ygor.helpdesk.domain.enums.Perfil;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Integer id;

	@NotNull(groups = {CreateGroup.class, UpdateGroup.class}, message = "O campo nome é obrigatório")
	protected String nome;

	@NotNull(groups = {CreateGroup.class, UpdateGroup.class}, message = "O campo CPF é obrigatório")
	protected String cpf;

	@NotNull(groups = {CreateGroup.class, UpdateGroup.class}, message = "O campo Email é obrigatório")
	protected String email;

	@NotNull(groups = CreateGroup.class, message = "O campo Senha é obrigatório")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected String senha;

	protected Set<Integer> perfis = new HashSet<>();

	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public ClienteDTO() {
		addPerfil(Perfil.CLIENTE);
	}

	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.perfis = obj.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}
}
