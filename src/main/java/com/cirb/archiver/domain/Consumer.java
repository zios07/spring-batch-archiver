package com.cirb.archiver.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Consumer {

	@Id
	@GeneratedValue
	private Long id;

	private String action;

	private String applicationId;

	private String endPoint;

	private String error;

	private String externalMessageId;

	private String keyType;

	private String keyValue;

	private String legalContent;

	private String request;

	private String requestTimeStamp;

	private String response;

	private String responseTimeStamp;

	private String transactionId;

	public Consumer() {
		super();
	}

	public Consumer(String action, String applicationId, String endPoint, String error,
			String externalMessageId, String keyType, String keyValue, String legalContent, String request,
			String requestTimeStamp, String response, String responseTimeStamp, String transactionId) {
		super();
		this.action = action;
		this.applicationId = applicationId;
		this.endPoint = endPoint;
		this.error = error;
		this.externalMessageId = externalMessageId;
		this.keyType = keyType;
		this.keyValue = keyValue;
		this.legalContent = legalContent;
		this.request = request;
		this.requestTimeStamp = requestTimeStamp;
		this.response = response;
		this.responseTimeStamp = responseTimeStamp;
		this.transactionId = transactionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getExternalMessageId() {
		return externalMessageId;
	}

	public void setExternalMessageId(String externalMessageId) {
		this.externalMessageId = externalMessageId;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getLegalContent() {
		return legalContent;
	}

	public void setLegalContent(String legalContent) {
		this.legalContent = legalContent;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequestTimeStamp() {
		return requestTimeStamp;
	}

	public void setRequestTimeStamp(String requestTimeStamp) {
		this.requestTimeStamp = requestTimeStamp;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponseTimeStamp() {
		return responseTimeStamp;
	}

	public void setResponseTimeStamp(String responseTimeStamp) {
		this.responseTimeStamp = responseTimeStamp;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
