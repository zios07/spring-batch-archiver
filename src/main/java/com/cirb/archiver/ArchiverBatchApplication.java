package com.cirb.archiver;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class ArchiverBatchApplication {

	protected final Log logger = LogFactory.getLog(getClass());

	private final Job archivingJob;

	private final JobLauncher jobLauncher;

	@Autowired
	public ArchiverBatchApplication(Job archivingJob, JobLauncher jobLauncher) {
		super();
		this.archivingJob = archivingJob;
		this.jobLauncher = jobLauncher;
	}

	public static void main(String[] args) {
		SpringApplication.run(ArchiverBatchApplication.class, args);
	}
	
	@Scheduled(cron = "${batch.archivingCron}")
	public void launchMigration() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		logger.info("***************** Starting Archiving Batch *****************");

		JobParameters params = new JobParametersBuilder().addLong("commit.interval", 100L).addDate("date", new Date())
				.toJobParameters();
		jobLauncher.run(archivingJob, params);
		logger.info("***************** Archiving Completed! *****************");
	}
	
	
}
