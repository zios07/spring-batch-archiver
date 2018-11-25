package com.cirb.archiver.batch.tasklets;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.cirb.archiver.domain.SolrArchive;
import com.cirb.archiver.repositories.SolrArchiveRepository;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

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
				String extension = FilenameUtils.getExtension(listOfFiles[i].getName());
				String fileName = FilenameUtils.getBaseName(listOfFiles[i].getName());
        SolrArchive archive = new SolrArchive(new Date(), readFileToByteArray(listOfFiles[i]), extension, fileName);
				this.solrArchiveRepository.save(archive);
//				Files.delete(Paths.get(listOfFiles[i].getPath()));
			}
		}
		return RepeatStatus.FINISHED;
	}

}
