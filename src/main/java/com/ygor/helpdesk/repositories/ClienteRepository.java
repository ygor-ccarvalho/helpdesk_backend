package com.ygor.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ygor.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
