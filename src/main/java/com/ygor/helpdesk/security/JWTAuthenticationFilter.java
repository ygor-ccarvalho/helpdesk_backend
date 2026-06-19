package com.ygor.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ygor.helpdesk.domain.dtos.CredenciaisDTO;
import com.ygor.helpdesk.resources.exceptions.StandardError;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					creds.getEmail(), creds.getSenha(), new ArrayList<>());
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			return authentication;

		} catch (IOException e) {
		    throw new RuntimeException("Falha ao ler credenciais", e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		UserSS usuario = (UserSS) authResult.getPrincipal();
	    String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getNome());
		response.setHeader("access-control-expose-headers", "Authorization");
		response.setHeader("Authorization", "Bearer " + token);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	        AuthenticationException failed) throws IOException, ServletException {

	    response.setStatus(401);
	    response.setContentType("application/json");
	    response.getWriter().append(json());
	}

	private CharSequence json() throws IOException {
	    long date = System.currentTimeMillis();

	    StandardError error = new StandardError(
	            date,                            
	            401,                             
	            "Não autorizado",                
	            "Email ou senha inválidos",      
	            "/login"                         
	    );

	    return new ObjectMapper().writeValueAsString(error);
	}
	}

