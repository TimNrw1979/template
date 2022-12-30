package de.tauiotamy.templates.springboot.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import net.minidev.json.JSONArray;

@EnableWebSecurity // Enable Spring Security’s web security support
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // To configure method-level security
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().and()
				.authorizeRequests(authz -> authz.requestMatchers("/").permitAll().anyRequest().authenticated())
				.oauth2Login().and().logout().logoutSuccessUrl("/");
		return http.build();
	}

	@Bean
	public GrantedAuthoritiesMapper userAuthoritiesMapper() {
		return (authorities) -> {
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

			Optional<OidcUserAuthority> awsAuthority = (Optional<OidcUserAuthority>) authorities.stream()
					.filter(grantedAuthority -> "ROLE_USER".equals(grantedAuthority.getAuthority())).findFirst();

			if (awsAuthority.isPresent()) {
				mappedAuthorities = ((JSONArray) awsAuthority.get().getAttributes().get("cognito:groups")).stream()
						.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toSet());
			}

			return mappedAuthorities;
		};
	}
}