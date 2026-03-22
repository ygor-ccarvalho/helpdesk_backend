package com.ygor.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ygor.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
