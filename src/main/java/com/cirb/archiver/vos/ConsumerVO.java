/**
 * 
 */
package com.cirb.archiver.vos;

import java.util.Date;

/**
 * @author Zkaoukab
 *
 */
public class ConsumerVO {


	private Long id;

	private String action;

	private String applicationId;

	private String endPoint;

	private String error;

	private String internalMessageId;

	private String externalMessageId;

	private String externalMessageContext;
	
	private String institute;

	private String keyType;

	private String request;

	private String requestTimestamp;

	private String response;

	private String responseTimestamp;

	private String transactionId;
	
	private Date externalTimestamp;

	public ConsumerVO() {
		super();
	}

	public ConsumerVO(Long id, String action, String applicationId, String endPoint, String error, String internalMessageId, String externalMessageId, String externalMessageContext, String institute, String keyType, String request, String requestTimestamp, String response, String responseTimestamp, String transactionId, Date externalTimestamp) {
		super();
		this.id = id;
		this.action = action;
		this.applicationId = applicationId;
		this.endPoint = endPoint;
		this.error = error;
		this.internalMessageId = internalMessageId;
		this.externalMessageId = externalMessageId;
		this.externalMessageContext = externalMessageContext;
		this.institute = institute;
		this.keyType = keyType;
		this.request = request;
		this.requestTimestamp = requestTimestamp;
		this.response = response;
		this.responseTimestamp = responseTimestamp;
		this.transactionId = transactionId;
		this.externalTimestamp = externalTimestamp;
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

	public String getInternalMessageId() {
		return internalMessageId;
	}

	public void setInternalMessageId(String internalMessageId) {
		this.internalMessageId = internalMessageId;
	}

	public String getExternalMessageId() {
		return externalMessageId;
	}

	public void setExternalMessageId(String externalMessageId) {
		this.externalMessageId = externalMessageId;
	}

	public String getExternalMessageContext() {
		return externalMessageContext;
	}

	public void setExternalMessageContext(String externalMessageContext) {
		this.externalMessageContext = externalMessageContext;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequestTimestamp() {
		return requestTimestamp;
	}

	public void setRequestTimestamp(String requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponseTimestamp() {
		return responseTimestamp;
	}

	public void setResponseTimestamp(String responseTimestamp) {
		this.responseTimestamp = responseTimestamp;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getExternalTimestamp() {
		return externalTimestamp;
	}

	public void setExternalTimestamp(Date externalTimestamp) {
		this.externalTimestamp = externalTimestamp;
	}


}
