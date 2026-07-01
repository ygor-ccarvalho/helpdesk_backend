package com.ygor.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ygor.helpdesk.domain.dtos.TecnicoDTO;
import com.ygor.helpdesk.domain.enums.Perfil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tecnico extends Pessoa {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
        addPerfil(Perfil.TECNICO);
    }

    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.TECNICO);
    }

    public Tecnico(TecnicoDTO obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
        addPerfil(Perfil.TECNICO);
    }
}
