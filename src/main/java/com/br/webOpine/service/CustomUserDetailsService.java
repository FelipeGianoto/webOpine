package com.br.webOpine.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.webOpine.model.User;
import com.br.webOpine.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(usernameOrEmail);

		if (user == null) {
			throw new UsernameNotFoundException("Usuário ou senha inválidos!");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList()));
	}

	public void authenticate(String usernameOrEmail, String password) {
		User user = userRepository.findByEmail(usernameOrEmail);

		if (user == null || !password.equals(user.getPassword())) {
			throw new UsernameNotFoundException("Usuário ou senha inválidos!");
		}
	}
}
