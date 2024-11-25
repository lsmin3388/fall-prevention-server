package com.happyaging.fallprevention;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
	private String secret;
	private String bearerType;
	private String authHeader;
	private Expiration expiration = new Expiration();

	@Getter @Setter
	public static class Expiration {
		private Integer access;
		private Integer refresh;
	}
}
