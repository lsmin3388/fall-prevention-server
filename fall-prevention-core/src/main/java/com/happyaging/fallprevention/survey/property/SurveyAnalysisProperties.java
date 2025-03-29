package com.happyaging.fallprevention.survey.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.survey.analysis")
public class SurveyAnalysisProperties {
	private String apiUrl;
}
