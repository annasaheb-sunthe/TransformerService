package com.scb.util;

public interface ReceiverConstants {
	
	public static final String TRANSFORMER_SERVICE_URL= "/transformer";
	public static final String JMS_SERVICE_REQUEST_HANDLE_URL="/publishMessage";
	public static final String HTTPS_SERVICE_REQUEST_HANDLE_URL="/httpspublishMessage";
	public static final String ADD_TRANSMITTER_URL="/addTransmitter";
	public static final String MODIFY_TRANSMITTER_URL="/modifyTransmitter";
	public static final String GET_TRANSMITTER_BY_TYPE_URL="/transmitterByType";
	public static final String GET_ALL_TRANSMITTER_URL="/allTransmitters";
	public static final String GET_TRANSMITTER_BY_ID_URL="/transmitterById/{transmitterId}";
}
