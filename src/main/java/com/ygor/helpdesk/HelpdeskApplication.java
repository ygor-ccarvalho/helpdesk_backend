package com.ygor.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ygor.helpdesk.domain.Chamado;
import com.ygor.helpdesk.domain.Cliente;
import com.ygor.helpdesk.domain.Tecnico;
import com.ygor.helpdesk.domain.enums.Perfil;
import com.ygor.helpdesk.domain.enums.Prioridade;
import com.ygor.helpdesk.domain.enums.Status;
import com.ygor.helpdesk.helpdesk_repositories.ChamadoRepository;
import com.ygor.helpdesk.helpdesk_repositories.ClienteRepository;
import com.ygor.helpdesk.helpdesk_repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Tecnico tec1 = new Tecnico(null, "Ygor Carvalho", "63653230268", "ygor@mail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "70517744013", "torvalds@mail.com","123" );
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01","Primeiro chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
		
	}

}
