package com.cirb.archiver.domain;

import java.nio.ByteBuffer;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "archive")
public class SolrArchive {

	@Id
	@Field
	@Indexed(name = "id", type = "long")
	private Long id;

	@Field
	@Indexed(name = "date", type = "date")
	private Date date;

	@Field
	@Indexed(name = "content")
	private ByteBuffer content;
	
	@Field
	private String extension;

	public SolrArchive(Date date, ByteBuffer content, String extension) {
		super();
		this.date = date;
		this.content = content;
		this.extension = extension;
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

	public ByteBuffer getContent() {
		return content;
	}

	public void setContent(ByteBuffer content) {
		this.content = content;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
}
