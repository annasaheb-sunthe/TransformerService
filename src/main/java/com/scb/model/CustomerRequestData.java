package com.scb.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EntityScan @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @XmlRootElement @XmlAccessorType(XmlAccessType.FIELD) 
public class CustomerRequestData {
	@Column
	private long customerId;
	@Column
	private String customerName;
	@Id
//	@Generated(strategy = Generated.IDENTITY)
	private int serviceId;
	@Column
	private String customerAccType;
	@Column
	private String customerRegion;
	@Column 
	private String timeStamp;
	@Column
	private long correlationId;
	@Column
	private long transactionId;
	@Column
	private String downStreamResponse;
	public long getCorrelationId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
