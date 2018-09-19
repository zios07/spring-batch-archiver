package com.cirb.archiver.batch.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cirb.archiver.batch.tasklets.EncryptionTasklet;

@Configuration
public class ArchivingJob {

	protected final Log logger = LogFactory.getLog(getClass());

	@Value("${batch.json-directory}")
	private String jsonDirectory;
	
	@Value("${batch.encryption-directory}")
	private String encryptionDirectory;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

//	@Autowired
//	private ConsumerRepository consumerRepository;
//
//	@Autowired
//	private ProviderRepository providerRepository;

	@Bean
	public Job archiverJob() {
		return jobBuilderFactory.get("archivingJob").incrementer(new RunIdIncrementer()).start(encryptingStep())
				.build();
	}


	/*
	 * 
	 * 
	 *  Encryption step config
	 * 
	 * 
	 */

	@Bean
	protected Tasklet encryptionTasklet() {
		return new EncryptionTasklet(jsonDirectory, encryptionDirectory);
	}
	
	@Bean
	protected Step encryptingStep() {
		return stepBuilderFactory.get("encryptingStep").tasklet(encryptionTasklet()).build();
	}
	

	
	
	/*
	 * 
	 * 
	 * 	Archiving step config
	 * 
	 * 
	 */
	

//	@Bean
//	@StepScope
//	protected Tasklet archivingTasklet() {
//		return new ArchivingTasklet(consumerRepository, providerRepository, writer());
//	}
//
//	@Bean
//	protected Step archivingStep() {
//		return stepBuilderFactory.get("archivingStep").tasklet(archivingTasklet()).build();
//	}
//	@Bean
//	@StepScope
//	public FlatFileItemWriter<Archive> writer() {
//		FlatFileItemWriter<Archive> writer = new FlatFileItemWriter<>();
//		writer.setResource(new FileSystemResource(jsonDirectory));
//		writer.setSaveState(true);
//		writer.open(new ExecutionContext());
//		writer.setLineAggregator(new ArchiveJsonItemAggregator());
//		return writer;
//	}
	
	
}
