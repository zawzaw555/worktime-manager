package com.zawzaw.worktime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
					"/webjars/**"
				).permitAll()
				.requestMatchers("/worker/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
					
				.anyRequest().authenticated()
			)
			
			.csrf( csrf -> csrf.disable())
			
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
			);
		
		return http.build();
	}
	
}
