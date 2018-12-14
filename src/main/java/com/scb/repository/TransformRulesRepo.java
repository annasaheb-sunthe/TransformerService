package com.scb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.scb.model.TransformRule;

@RepositoryRestResource
public interface TransformRulesRepo extends JpaRepository<TransformRule, Long> {

	@Query(value = "SELECT * FROM TransformRule sd WHERE sd.transactionType=?1", nativeQuery = true)
	List<TransformRule> findByType(String TransactionType);
	
	@Query(value = "SELECT * FROM TransformRule sd WHERE sd.transactionType=?1 AND sd.transactionSubType=?2 "
			+ "AND sd.sourceMessageType=?3", nativeQuery = true)
	List<TransformRule> findByTypes(String transactionType, String transactionSubType, String sourceMessageType);
	
	@Query(value = "SELECT * FROM TransformRule sd WHERE sd.transactionType=?1 AND sd.transactionSubType=?2 "
			+ "AND sd.sourceMessageType=?3", nativeQuery = true)
	List<TransformRule> findByTransactionTypeAndTransactionSubType(String transactionType, String transactionSubType, 
			String sourceMessageType);
}