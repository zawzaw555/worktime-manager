package com.zawzaw.worktime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.zawzaw.worktime.model.entity.ELogin;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFiltterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
					"/",
					"/login",
					"/css/**",
					"/js/**",
					"/image/**",
					"/webjars/**"
				).permitAll()
				.requestMatchers("/admin/**").access((authentication,context) ->
						new AuthorizationDecision(
								hasSessionRole(context.getRequest(),"ADMIN")
						)
				)			
				.requestMatchers("/worker/**").access((authentication,context) ->
					new AuthorizationDecision(
							hasSessionRole(context.getRequest(),"ADMIN","WORKER")
					)
				)
				.anyRequest().denyAll()
			)
						
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
			);
		http.headers(headers -> headers.frameOptions(option -> option.disable()));
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	private boolean hasSessionRole(
			HttpServletRequest request,String... roles) {
		
		var session = request.getSession(false);
		if(session == null) {
			return false;
		}
		
		Object value = session.getAttribute("loginUser");
		if (!(value instanceof ELogin user)) {
			return false;
		}
		
		for(String role : roles) {
			if (role.equals(user.getRole())) {
				return true;
			}
		}
		
		return false;
	}
	
}
