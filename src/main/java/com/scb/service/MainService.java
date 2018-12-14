package com.scb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scb.model.RequestData;
import com.scb.model.TransformRule;


@Service
public interface MainService {
	public boolean saveTransformRule(TransformRule transformrule);

	public List<TransformRule> getAllTransformRules();

	public TransformRule getTransformRuleById(long tranformRuleId);

	public List <TransformRule> getTransformRuleByType(String transactionType);
		
	public void ModifyTransformRule(TransformRule transformrule);

	public List<TransformRule> getTransformRuleByTypes(RequestData requestData);
	
	public void DeleteTransformRuleModel(long tranformRuleId);
}
