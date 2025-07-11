package com.example.ex01.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.siot.IamportRestClient.IamportClient;

@Configuration
public class IamportConfig {
	@Value("${imp.key}")
	private String apiKey;
	@Value("${imp.secret}")
	private String secretKey;
	
	@Bean
	public IamportClient iamportClient() {
		return new IamportClient(apiKey, secretKey);
	}
}
