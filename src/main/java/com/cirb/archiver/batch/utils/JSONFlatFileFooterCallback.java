package com.cirb.archiver.batch.utils;

import java.io.IOException;
import java.io.Writer;
import org.springframework.batch.item.file.FlatFileFooterCallback;

public class JSONFlatFileFooterCallback implements FlatFileFooterCallback {

	@Override
	public void writeFooter(Writer writer) throws IOException {
	  System.out.println("Jessussssss");
		writer.write("]"); 
	}
}
