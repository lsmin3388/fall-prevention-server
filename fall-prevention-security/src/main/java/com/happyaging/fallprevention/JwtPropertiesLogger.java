package com.happyaging.fallprevention;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtPropertiesLogger {

	private final JwtProperties jwtProperties;

	public JwtPropertiesLogger(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	@PostConstruct
	public void logJwtProperties() {
		System.out.println("test222");
		log.info("test");
		log.info("JWT Properties Loaded: secret={}, bearerType={}, authHeader={}, expiration.access={}, expiration.refresh={}",
			jwtProperties.getSecret(),
			jwtProperties.getBearerType(),
			jwtProperties.getAuthHeader(),
			jwtProperties.getExpiration().getAccess(),
			jwtProperties.getExpiration().getRefresh());
	}
}
