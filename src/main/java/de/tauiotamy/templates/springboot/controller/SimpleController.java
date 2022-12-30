package de.tauiotamy.templates.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleController {
	@Value("${spring.application.name}")
	String appName;

	@GetMapping("/hello")
	public String homePage(Model model) {
		model.addAttribute("appName", appName);
		return "home";
	}

	@GetMapping("/hello2")
	public String getIndexPage(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
				model.addAttribute("secretMessage", "Admin message is s3crEt");
			} else {
				model.addAttribute("secretMessage", "Lorem ipsum dolor sit amet");
			}
		}

		model.addAttribute("message", "AWS Cognito with Spring Security");

		return "home";
	}
}
