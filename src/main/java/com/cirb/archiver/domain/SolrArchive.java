package com.cirb.archiver.domain;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "archives")
public class SolrArchive {

	@Id
	@Field
	private Long id;

	@Field
	private Date date;

	@Field
	private byte[] content;
	
	@Field
	private String extension;
	
	@Field
	private String fileName;

	public SolrArchive(Date date, byte[] content, String extension, String fileName) {
		super();
		this.date = date;
		this.content = content;
		this.extension = extension;
		this.fileName = fileName;
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

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
