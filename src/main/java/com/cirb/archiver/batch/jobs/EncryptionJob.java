package com.cirb.archiver.batch.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cirb.archiver.batch.tasklets.EncryptionTasklet;

@Configuration
public class EncryptionJob {
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Value("${batch.output-directory}")
	private String inputDirectory;


	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	@StepScope
	protected Tasklet archivingTasklet() {
		return new EncryptionTasklet();
	}

	@Bean
	protected Step archivingTaskletStep() {
		return stepBuilderFactory.get("archivingTaskletStep").tasklet(archivingTasklet()).build();
	}

	@Bean
	public Job administrationJob() {
		return jobBuilderFactory.get("archivingJob").incrementer(new RunIdIncrementer()).start(archivingTaskletStep())
				.build();
	}
	
	// TODO writer and reader
	
}
