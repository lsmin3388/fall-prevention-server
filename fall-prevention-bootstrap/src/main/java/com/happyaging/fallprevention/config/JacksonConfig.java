package com.happyaging.fallprevention.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;

@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL);
	}
}
