package com.cirb.archiver.batch.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.file.transform.LineAggregator;

import com.cirb.archiver.domain.Archive;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ArchiveJsonItemAggregator implements LineAggregator<Archive> {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public String aggregate(Archive item) {
		String result = null;
		try {
			result = JsonUtils.convertObjectToJsonString(item); 
		} catch (JsonProcessingException jpe) {
			logger.error("An error has occured. Error message : "+ jpe.getMessage() );
		}
		return result;
	}

}
