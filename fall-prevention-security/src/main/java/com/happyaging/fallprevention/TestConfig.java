package com.happyaging.fallprevention;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestConfig {
	private final JwtProperties jwtProperties;

	@PostConstruct
	public void test() {
		System.out.println("TestConfig");
		log.info("secret: {}, bearerType: {}, authHeader: {}, access: {}, refresh: {}",
			jwtProperties.getSecret(), jwtProperties.getBearerType(),
			jwtProperties.getAuthHeader(), jwtProperties.getExpiration().getAccess(),
			jwtProperties.getExpiration().getRefresh());
	}
}
