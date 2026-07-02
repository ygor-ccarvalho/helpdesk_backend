package com.ygor.helpdesk.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ygor.helpdesk.domain.Pessoa;
import com.ygor.helpdesk.repositories.PessoaRepository;
import com.ygor.helpdesk.security.UserSS;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final PessoaRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Pessoa user = repository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));

		return new UserSS(user.getId(), user.getEmail(), user.getNome(), user.getSenha(), user.getPerfis());
	}
}
