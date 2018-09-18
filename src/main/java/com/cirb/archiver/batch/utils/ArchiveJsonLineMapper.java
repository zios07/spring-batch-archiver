package com.cirb.archiver.batch.utils;

import org.springframework.batch.item.file.LineMapper;

import com.cirb.archiver.domain.Archive;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ArchiveJsonLineMapper implements LineMapper<Archive> {

    private ObjectMapper mapper = new ObjectMapper();


    /**
     * Interpret the line as a Json object and create a Blub Entity from it.
     * 
     * @see LineMapper#mapLine(String, int)
     */
    @Override
    public Archive mapLine(String line, int lineNumber) throws Exception {
        return mapper.readValue(line, Archive.class);
    }

}