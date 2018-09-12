package com.cirb.archiver.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Archive {

	@Id @GeneratedValue
	private Long id;
	
	private Date date;
	
	@OneToOne
	private Consumer consumer;
	
	@OneToOne
	private Provider provider;

	public Archive() {
		super();
	}

	public Archive(Date date, Consumer consumer, Provider provider) {
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
