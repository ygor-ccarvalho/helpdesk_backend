package com.ygor.helpdesk.helpdesk_repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ygor.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
