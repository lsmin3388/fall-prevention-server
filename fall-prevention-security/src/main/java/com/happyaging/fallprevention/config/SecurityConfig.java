package com.happyaging.fallprevention.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.happyaging.fallprevention.security.filter.AccessTokenAuthorizationFilter;
import com.happyaging.fallprevention.security.filter.RefreshTokenReissueFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final AccessTokenAuthorizationFilter accessTokenAuthorizationFilter;
	private final RefreshTokenReissueFilter refreshTokenReissueFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(
		HttpSecurity httpSecurity
	) throws Exception {
		httpSecurity
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable);

		httpSecurity
			.authorizeHttpRequests(request -> request
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
				.anyRequest().authenticated())
			.addFilterBefore(accessTokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(refreshTokenReissueFilter, AccessTokenAuthorizationFilter.class)
			.httpBasic(AbstractHttpConfigurer::disable);

		return httpSecurity.build();
	}
}
