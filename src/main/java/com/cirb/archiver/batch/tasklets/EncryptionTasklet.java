package com.cirb.archiver.batch.tasklets;

import java.security.Security;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.cirb.archiver.batch.utils.JavaPGP;
import com.cirb.archiver.domain.Archive;

//@Component
public class EncryptionTasklet implements Tasklet {
//
//	@Value("${batch.output-directory}")
//	private String inputDirectory;
//	
	private ItemWriter<Archive> writer;
	
	public EncryptionTasklet(FlatFileItemWriter<Archive> writer) {
		this.writer = writer;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
        MultiResourceItemReader<String> reader = new MultiResourceItemReader<>();
//        reader.setResources(new Resource[] {new FileSystemResource(inputDirectory)});
        reader.setDelegate(new FlatFileItemReader<>());
        byte[] encryptedFile = JavaPGP.encrypt(IOUtils.toByteArray(reader.getCurrentResource().getInputStream()), JavaPGP.generateKeyPair().getPublic());
//        writer.write(encryptedFile.);
        return RepeatStatus.FINISHED;
	}

}
