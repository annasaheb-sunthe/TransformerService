package com.scb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scb.model.RequestData;
import com.scb.model.TransformRule;


@Service
public interface MainService {
	boolean saveTransformRule(TransformRule transformrule);

	List<TransformRule> getAllTransformRules();

	TransformRule getTransformRuleById(long tranformRuleId);

	List <TransformRule> getTransformRuleByType(String transactionType);
		
	void ModifyTransformRule(TransformRule transformrule);

	public List<TransformRule> getTransformRuleByTypes(RequestData requestData);
}
