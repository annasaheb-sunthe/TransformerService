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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.scb.model.AuditLog;
import com.scb.model.RequestData;
import com.scb.model.TransformRule;
import com.scb.service.CustomerRequestService;
import com.scb.service.MainService;
import com.scb.serviceImpl.InternalApiInvoker;
import com.scb.util.ReceiverConstants;
import com.scb.util.ServiceUtil;

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

	@Autowired
	private ServiceUtil commonMethods;

	@Autowired
	private InternalApiInvoker internalApiInvoker;
	
	@RequestMapping("/transformRequestHandler")
	public ResponseEntity<RequestData> requestDataHandle(@RequestBody RequestData requestData) {
		log.debug("Request Data received - transactionType :" + requestData.getTransactionType() 
				+ ", transactionSubType : " + requestData.getTransactionSubType()
				+ ", payloadForamt : " + requestData.getPayloadFormat());
		
		AuditLog auditLog = commonMethods.getAuditLog(requestData, "INITIATED", "Request data transformation initiated");
		ResponseEntity<AuditLog> responseAuditLog = internalApiInvoker.auditLogApiCall(auditLog);
	
		RequestData transformResponse = customerRequestService.getParseRequestData(requestData);
		
		
		log.debug("transformResponse :" +transformResponse.toString());
		
		if (transformResponse.getPayload() == null) {
			auditLog = commonMethods.getAuditLog(requestData, "FAILED", "Failed to transform request message for transaction type: " + requestData.getTransactionType());
		} else {
			auditLog = commonMethods.getAuditLog(requestData, "COMPLETED", "Message transformation for transaction type: " + requestData.getTransactionType() + " successfully");
		}

		responseAuditLog = internalApiInvoker.auditLogApiCall(auditLog);
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
	
	@RequestMapping(value = ReceiverConstants.DELETE_TRANSFORMER_URL, method = RequestMethod.DELETE, produces = {"application/xml", "application/json"})
    public ResponseEntity<Void> deleteDupcheckRules(@PathVariable("transformerId") long transformerId) {
		mainservice.DeleteTransformRuleModel(transformerId);
        return new ResponseEntity<Void>(HttpStatus.OK);  	
    }
}
