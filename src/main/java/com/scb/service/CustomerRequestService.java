package com.scb.service;

import org.springframework.stereotype.Service;

import com.scb.model.CustomerRequestData;
import com.scb.model.CustomerResponse;
import com.scb.model.RequestData;

@Service
public interface CustomerRequestService {
	
	public CustomerResponse customerRequestHandleService(CustomerRequestData customerRequestData);

	public RequestData getParseRequestData(RequestData requestData);

	
}
