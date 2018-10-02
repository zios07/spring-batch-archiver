package com.cirb.archiver.batch.tasklets;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.cirb.archiver.batch.utils.JavaPGP;

public class EncryptionTasklet implements Tasklet {

	private String path;

	public EncryptionTasklet(String path) {
		this.path = path;
	}
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		File file = new File(path + "/archive_24-09-2018.json");
		byte[] content = FileUtils.readFileToByteArray(file);
		byte[] encryptedContent = JavaPGP.encrypt(content, JavaPGP.generateKeyPair().getPublic());
		FileUtils.writeByteArrayToFile(new File(path + "/encrypted/" + file.getName()), encryptedContent);
		file.delete();
		return RepeatStatus.FINISHED;
	}

}
