package com.br.webOpine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String loginForm(@RequestParam(name = "error", required = false) boolean erro,
			@RequestParam(name = "qrcodeURI", required = false) String qrcodeUri, Model model,
			HttpServletResponse response) {
		
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(@RequestParam(value = "sessao", required = false) boolean sessao, HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}

}
