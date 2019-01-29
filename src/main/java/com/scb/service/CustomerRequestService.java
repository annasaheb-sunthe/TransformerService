package com.scb.service;

import org.springframework.stereotype.Service;

import com.scb.model.CustomerRequestData;
import com.scb.model.CustomerResponse;
import com.scb.model.RequestData;
import com.scb.model.ResponseMessage;

@Service
public interface CustomerRequestService {
	
	public CustomerResponse customerRequestHandleService(CustomerRequestData customerRequestData);

	public ResponseMessage getParseRequestData(RequestData requestData);

	
}
