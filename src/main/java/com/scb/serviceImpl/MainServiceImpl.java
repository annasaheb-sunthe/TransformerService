package com.scb.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scb.model.RequestData;
import com.scb.model.TransformRule;
import com.scb.repository.TransformRulesRepo;
import com.scb.service.MainService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MainServiceImpl implements MainService {
	@Autowired
	private TransformRulesRepo transformrepo;
	@Autowired
	private MainService mainservice;

	/*
	 * public List<String> XMLServiceValidation(String XPath) { XPathConverstion
	 * xpathconvert = new XPathConverstion();
	 * List<String>rules=xpathconvert.XPathConversion(XPath); return rules; }
	 */

	@Override
	public void ModifyTransformRule(TransformRule transformRule) {
		transformRule.setUpdatedOn(getCurrentDateTime());
		transformrepo.save(transformRule);
	}

	@Override
	public boolean saveTransformRule(TransformRule transformrule) {
		log.info("Transform Rule received: " + transformrule.getTranformRuleId());
		transformrule.setTranformRuleId(getTransfomerId());
		transformrule.setCreatedOn(getCurrentDateTime());
		transformrule.setUpdatedOn(getCurrentDateTime());

		TransformRulesRepo persistDataVar = null;
		try {
			persistDataVar = (TransformRulesRepo) transformrepo.findById(transformrule.getTranformRuleId()).get();
		} catch (NoSuchElementException ex) {
			log.info("Error in finding rule" + ex.getMessage());
		}
		if (persistDataVar != null) {
			return false;
		} else {
			log.info("Rule details being saved in db");
			transformrepo.save(transformrule);
			log.info("Rule details saved in db");
			return true;
		}

	}

	@Override
	public List<TransformRule> getAllTransformRules() {
		List<TransformRule> list = new ArrayList<>();
		transformrepo.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public List<TransformRule> getTransformRuleByTypes(RequestData requestData) {
		List<TransformRule> list = transformrepo.findByTypes(requestData.getTransactionType(), 
				requestData.getTransactionSubType(), requestData.getPayloadFormat());
		return list;
	}

	@Override
	public List<TransformRule> getTransformRuleByType(String transactionType) {
		List<TransformRule> list = transformrepo.findByType(transactionType);
		return list;
	}
	@Override
	public TransformRule getTransformRuleById(long tranformRuleId) {
		TransformRule obj = transformrepo.findById(tranformRuleId).get();
		return obj;
	}

	@Override
	public void DeleteTransformRuleModel(long tranformRuleId) {
		transformrepo.delete(getTransformRuleById(tranformRuleId));
	}

	public long getTransfomerId() {
		Random random = new Random(System.nanoTime() % 100000);
		long uniqueMetadataId = random.nextInt(1000000000);
		return uniqueMetadataId;
	}

	public String getCurrentDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.toString();
	}
}
