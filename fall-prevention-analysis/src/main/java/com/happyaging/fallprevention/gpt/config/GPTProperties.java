package com.happyaging.fallprevention.gpt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "openai")
public class GPTProperties {
	private String apiUrl;
	private String apiKey;
}
