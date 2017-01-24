package com.expenses.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "data")
public class DatabaseConfiguration {
	private Object mongodb;

	public Object getMongodb() {
		return mongodb;
	}

	public void setMongodb(Object mongodb) {
		this.mongodb = mongodb;
	}
}
