package com.br.webOpine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(c -> c.disable()).authorizeHttpRequests((requests) -> requests
				.requestMatchers("/login", "/login-clientWeb", "/style.css", "/favicon.ico", "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/assets/**", "*.css").permitAll()
				.requestMatchers("/home").hasAnyAuthority("ADMIN", "USUARIO").anyRequest()
				.authenticated())
				.formLogin((form) -> form.loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/home").usernameParameter("email").passwordParameter("password"))
				.logout((logout) -> logout.permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login"))
				.exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/access-denied"));
		return http.build();
	}

}