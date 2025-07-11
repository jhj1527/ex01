package com.example.ex01.service;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

public interface PaymentService {
	IamportResponse<Payment> paymentByImpUid(String imp_uid) throws IamportResponseException, IOException;

	String getToken() throws JsonProcessingException, IOException, InterruptedException, ParseException;
}
