package com.cirb.archiver.batch.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.file.transform.LineAggregator;

import com.cirb.archiver.domain.JsonArchive;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ArchiveJsonItemAggregator implements LineAggregator<JsonArchive> {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public String aggregate(JsonArchive item) {
		String result = null;
		try {
			result = JsonUtils.convertObjectToJsonString(item); 
		} catch (JsonProcessingException jpe) {
			logger.error("An error has occured. Error message : "+ jpe.getMessage() );
		}
		return result;
	}

}
