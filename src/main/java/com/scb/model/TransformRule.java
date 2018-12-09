package com.scb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @Entity @Table(name="TransformRule") @NoArgsConstructor @AllArgsConstructor @ToString @XmlRootElement
public class TransformRule {
	@Id
	@Column
	private long tranformRuleId;
	@Column
	private String transactionType;
	@Column
	private String transactionSubType;
	@Column
	private String sourceMessageType;
	@Column
	private String targetMessageType;
	@Column (length = 100000)
	private String schema;
	@Column
	private String createdOn;
	@Column
	private String updatedOn;
	
}
