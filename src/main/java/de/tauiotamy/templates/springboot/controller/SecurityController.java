package de.tauiotamy.templates.springboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security")
public class SecurityController {

	@GetMapping(value = "username")
	public String currentUserName(Authentication authentication) {
		return authentication.getName();
	}
}
