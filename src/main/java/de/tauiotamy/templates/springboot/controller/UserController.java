package de.tauiotamy.templates.springboot.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import de.tauiotamy.templates.springboot.model.basic.User;

@Controller
//@RestController
@RequestMapping("user")
public class UserController {

	/**
	 * Logger.
	 */
	private static Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping("details")
	public String details(Model model, OAuth2AuthenticationToken authentication) {
		OAuth2AuthorizedClient client = authorizedClientService
				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

		if (StringUtils.isEmpty(userInfoEndpointUri)) {
			LOG.error("User endpoint not valid!");
		}

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
		HttpEntity entity = new HttpEntity("", headers);
		ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
		Map userAttributes = response.getBody();

		String usernName = (String) userAttributes.get("username");
		String foreName = (String) userAttributes.get("name");
		String surName = (String) userAttributes.get("family_name");
		String email = (String) userAttributes.get("email");
		String sub = (String) userAttributes.get("sub");

		User userDetails = User.builder().userName(usernName).sub(sub).foreName(foreName).surName(surName).email(email)
				.build();

		model.addAttribute("details", userDetails);

		return "/user/details.xhtml";
	}

}
