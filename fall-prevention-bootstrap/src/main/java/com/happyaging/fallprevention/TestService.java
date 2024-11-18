package com.happyaging.fallprevention;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestService {
	@PostConstruct
	public void test() {
		System.out.println("TestService");
		log.info("TestService");
	}
}
