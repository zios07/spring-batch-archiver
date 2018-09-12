package com.cirb.archiver.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.cirb.archiver.batch.tasklets.PostgresToCsv;
import com.cirb.archiver.domain.Archive;
import com.cirb.archiver.repositories.ConsumerRepository;
import com.cirb.archiver.repositories.ProviderRepository;

@Configuration
public class ArchivingBatch {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private ProviderRepository providerRepository;

	@Bean
	@StepScope
	protected Tasklet postgresToCsv() {
		return new PostgresToCsv(consumerRepository, providerRepository, writer());
	}

	@Bean
	protected Step postgresToCsvStep() {
		return stepBuilderFactory.get("postgresToCsvStep").tasklet(postgresToCsv()).build();
	}

	@Bean
	public Job administrationJob() {
		return jobBuilderFactory.get("archivingJob").incrementer(new RunIdIncrementer()).start(postgresToCsvStep())
				.build();
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<Archive> writer() {
		FlatFileItemWriter<Archive> writer = new FlatFileItemWriter<Archive>();
		writer.setResource(new FileSystemResource("csv/outputs/domain.all.csv"));
		writer.setAppendAllowed(true);
		writer.setLineAggregator(new DelimitedLineAggregator<Archive>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<Archive>() {
					{
						// setNames(new String[] { "id", "action", "applicationId", "endPoint", "error",
						// "externalMessageId", "keyType", "keyValue", "legalContent" });
						setNames(new String[] { "id", "date", "consumer", "provider" });
					}
				});
			}
		});
		return writer;
	}

}
