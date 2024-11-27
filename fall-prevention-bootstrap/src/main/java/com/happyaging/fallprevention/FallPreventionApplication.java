package com.happyaging.fallprevention;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableCaching
@SpringBootApplication
public class FallPreventionApplication {
	public static void main(String[] args) {
		SpringApplication.run(FallPreventionApplication.class, args);
	}
}
