package com.cirb.archiver.batch.tasklets;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class EncryptionTasklet implements Tasklet {

	private String path;

	public EncryptionTasklet(String path) {
		this.path = path;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		File file = new File(path);
		File[] listOfFiles = file.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				// byte[] content = FileUtils.readFileToByteArray(listOfFiles[i]);
				// byte[] encryptedContent = JavaPGP.encrypt(content);
				// FileUtils.writeByteArrayToFile(new File(path + listOfFiles[i].getName()),
				// encryptedContent);
			}
		}
		return RepeatStatus.FINISHED;
	}

}