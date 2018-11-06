package com.cirb.archiver.domain;

import java.util.Date;

import com.cirb.archiver.vos.ConsumerVO;
import com.cirb.archiver.vos.ProviderVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class JsonArchive {

	@JsonInclude(Include.NON_NULL)
	private Long id;

	private Date date;

	private Consumer consumer;

	private Provider provider;

	public JsonArchive() {
		super();
	}

	public JsonArchive(Date date, Consumer consumer, Provider provider) {
		super();
		this.date = date;
		this.consumer = consumer;
		this.provider = provider;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

}
