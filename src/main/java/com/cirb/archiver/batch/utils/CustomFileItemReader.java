package com.cirb.archiver.batch.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.FileSystemResource;

import com.cirb.archiver.domain.JsonArchive;

public class CustomFileItemReader<T> extends FlatFileItemReader<JsonArchive> {

	protected final Log logger = LogFactory.getLog(getClass());

	public void setResource(String path) {
	    super.setResource(new FileSystemResource(path));
	}
	
}
