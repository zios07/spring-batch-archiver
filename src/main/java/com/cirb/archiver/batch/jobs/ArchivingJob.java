package com.cirb.archiver.batch.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.cirb.archiver.batch.utils.ArchiveJsonItemAggregator;
import com.cirb.archiver.batch.utils.ArchiveJsonLineMapper;
import com.cirb.archiver.batch.utils.CustomFileItemReader;
import com.cirb.archiver.domain.Archive;

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
	protected Step encryptingStep() {
		return stepBuilderFactory.get("encryptingStep")
				.<Archive, Archive>chunk(1)
				.reader(jsonReader())
				.writer(jsonItemWriter())
				.processor(jsonProcessor())
				.build();
	}
	
	@Bean
	public ItemReader<Archive> jsonReader() {
		CustomFileItemReader<Archive> reader = new CustomFileItemReader<>();
		reader.setResource(jsonDirectory);
		reader.setLineMapper(new ArchiveJsonLineMapper());
		return reader;
	}
	
	public ItemProcessor<Archive, Archive> jsonProcessor() {
		ItemProcessor<Archive, Archive> processor = new JsonItemProcessor();
		return processor;
	}
	
	@Bean
	public FlatFileItemWriter<Archive> jsonItemWriter() {
		FlatFileItemWriter<Archive> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource(encryptionDirectory));
		writer.setSaveState(true);
		writer.open(new ExecutionContext());
		writer.setLineAggregator(new ArchiveJsonItemAggregator());
		return writer;
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
	
	static class JsonItemProcessor implements ItemProcessor<Archive, Archive> {

		@Override
		public Archive process(Archive item) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
