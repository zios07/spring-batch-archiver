package com.cirb.archiver.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "provider_audit")
public class Provider {

	@Id
	@GeneratedValue
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

	private String keyValue;

	private String legalContext;

	private String request;

	private String requestTimestamp;

	private String response;

	private String responseTimestamp;

	private String transactionId;
	
	@Temporal(TemporalType.DATE)
	private Date externalTimestamp;

  private transient boolean encrypted;

	public Provider() {
		super();
	}

	public Provider(String action, String applicationId, String endPoint, String error, String internalMessageId, String externalMessageId, String externalMessageContext, String institute, String keyType, String keyValue, String legalContext, String request, String requestTimestamp, String response, String responseTimestamp, String transactionId, Date externalTimestamp) {
		super();
		this.action = action;
		this.applicationId = applicationId;
		this.endPoint = endPoint;
		this.error = error;
		this.internalMessageId = internalMessageId;
		this.externalMessageId = externalMessageId;
		this.externalMessageContext = externalMessageContext;
		this.institute = institute;
		this.keyType = keyType;
		this.keyValue = keyValue;
		this.legalContext = legalContext;
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

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getLegalContext() {
		return legalContext;
	}

	public void setLegalContext(String legalContext) {
		this.legalContext = legalContext;
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

  public boolean isEncrypted() {
    return encrypted;
  }

  public void setEncrypted(boolean encrypted) {
    this.encrypted = encrypted;
  }
}
