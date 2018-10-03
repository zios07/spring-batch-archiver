package com.cirb.archiver.batch.tasklets;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.cirb.archiver.batch.utils.JavaPGP;
import com.cirb.archiver.domain.SolrArchive;
import com.cirb.archiver.repositories.SolrArchiveRepository;

public class SolrTasklet implements Tasklet {

	private SolrArchiveRepository solrArchiveRepository;

	private String path;
	
	public SolrTasklet(SolrArchiveRepository solrArchiveRepository, String path) {
		this.solrArchiveRepository = solrArchiveRepository;
		this.path = path;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		File file = new File(path);
		File[] listOfFiles = file.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				byte[] content = FileUtils.readFileToByteArray(listOfFiles[i]);
				byte[] encryptedContent = JavaPGP.encrypt(content, JavaPGP.generateKeyPair().getPublic());
				String extension = FilenameUtils.getExtension(listOfFiles[i].getName());
				SolrArchive archive = new SolrArchive(new Date(), ByteBuffer.wrap(encryptedContent), extension);
				this.solrArchiveRepository.save(archive);
				listOfFiles[i].delete();
			}
		}
		return RepeatStatus.FINISHED;
	}

}
