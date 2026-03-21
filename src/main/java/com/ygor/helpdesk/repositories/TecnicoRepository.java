package com.ygor.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ygor.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

}
