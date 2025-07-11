package com.example.ex01.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	@Value("${imp.key}")
	private String apiKey;
	@Value("${imp.secret}")
	private String secretKey;
	private final String TOKEN_URL = "https://api.iamport.kr/users/getToken";
	private final IamportClient iamportClient;
	
	@Autowired
	public PaymentServiceImpl(IamportClient iamportClient) {
		this.iamportClient = iamportClient;
	}
	
	@Override
	public String getToken() throws IOException, InterruptedException, ParseException {
		Map<String, Object> map = new HashMap<>();

		map.put("imp_key", apiKey);
		map.put("imp_secret", secretKey);
		
		JSONParser parser = new JSONParser();
		JSONObject object = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(map);
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(TOKEN_URL))
				.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				.method("POST", HttpRequest.BodyPublishers.ofString(result)).build();
		
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		
		object = (JSONObject) parser.parse(response.body());
		
		JSONObject res = (JSONObject) object.get("response");

		log.info("token:::::::::::::::" + res.get("access_token"));
		String token = (String) res.get("access_token");
		return token;
	}

	@Override
	public IamportResponse<Payment> paymentByImpUid(String imp_uid) throws IamportResponseException, IOException {
//		IamportClient iamportClient = new IamportClient(apiKey, secretKey);
		return iamportClient.paymentByImpUid(imp_uid);
	}

}
