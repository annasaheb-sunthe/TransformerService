package com.scb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.scb.model.CustomerRequestData;
import com.scb.model.CustomerResponse;
import com.scb.model.RequestData;
import com.scb.model.TransformRule;
import com.scb.service.CustomerRequestService;
import com.scb.service.MainService;
import com.scb.util.ReceiverConstants;

import lombok.extern.log4j.Log4j2;

@Component
@RestController 
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(ReceiverConstants.TRANSFORMER_SERVICE_URL)
public class TransformRulesController {
	@Autowired
	private MainService mainservice;
	
	@Autowired
	private CustomerRequestService customerRequestService;

	@RequestMapping("/transformRequestHandler")
	public ResponseEntity<RequestData> requestDataHandle(
			@RequestBody RequestData requestData) {
		RequestData transformResponse = customerRequestService.getParseRequestData(requestData);
		log.debug("transformResponse :" +transformResponse.toString());
		return new ResponseEntity<RequestData>(transformResponse, HttpStatus.OK);
	}
	
	/*@RequestMapping("/transformRequestHandler1")
	public ResponseEntity<CustomerResponse> customerRequestHandle(
			@RequestBody CustomerRequestData customerRequestData) {
		CustomerResponse customerResponse = customerRequestService.customerRequestHandleService(customerRequestData);
		return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.OK);
	}*/

	@PostMapping("/AddTransformRules")
	public ResponseEntity<Void> saveTransformRule(@RequestBody TransformRule transformrule, UriComponentsBuilder builder) {
		boolean flag = mainservice.saveTransformRule(transformrule);
		
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		HttpHeaders headers = new HttpHeaders();
		//headers.setLocation(builder.path("/getTransformRulesById/{transformRuleId}").buildAndExpand(transformrule).toUri());
		
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@GetMapping("/getAllTransformRules")
	public ResponseEntity<List<TransformRule>> getAllTransformRules() {
		log.info(" Get All Transaction received: ");
		List<TransformRule> list = mainservice.getAllTransformRules();
		log.info("Transaction Recieved " + list);
		return new ResponseEntity<List<TransformRule>>(list, HttpStatus.OK);
	}

	@GetMapping("/getTransformRulesById/{transformRuleId}")
	public ResponseEntity<TransformRule> getTransformRuleById(@PathVariable("transformRuleId") long transformRuleId) {
		log.info(" Get Transaction By ID received: " + transformRuleId);
		TransformRule transactionById = mainservice.getTransformRuleById(transformRuleId);
		log.info("Transaction Recieved With Id" + transformRuleId + " received: " + transactionById);
		return new ResponseEntity<TransformRule>(transactionById, HttpStatus.OK);
	}

	@PutMapping("/ModifyTranformRule/{id}")
	public ResponseEntity<TransformRule> ModifyTransformRule(@RequestBody TransformRule transformRule) {
		mainservice.ModifyTransformRule(transformRule);
		return new ResponseEntity<TransformRule>(transformRule, HttpStatus.OK);
	}
}
