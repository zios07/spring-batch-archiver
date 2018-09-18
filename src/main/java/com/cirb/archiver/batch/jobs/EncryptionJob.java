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
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.cirb.archiver.batch.tasklets.EncryptionTasklet;
import com.cirb.archiver.batch.utils.ArchiveJsonItemAggregator;
import com.cirb.archiver.batch.utils.ArchiveJsonLineMapper;
import com.cirb.archiver.domain.Archive;

@Configuration
public class EncryptionJob {
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Value("${batch.output-directory}")
	private String inputDirectory;

	@Value("${batch.encryption-directory}")
	private String outputDirectory;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
//	@Bean
//	@StepScope
//	protected Tasklet archivingTasklet() {
//		return new EncryptionTasklet(writer());
//	}

	@Bean
	protected Step encryptionStep() {
		return stepBuilderFactory.get("encryptionStep")
				.<Archive, Archive>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	@Bean
	public Job encryptionsJob() {
		return jobBuilderFactory.get("encryptionJob").incrementer(new RunIdIncrementer()).start(encryptionStep())
				.build();
	}
	
	@Bean
	public ItemProcessor<Archive, Archive> processor() {
		return new ItemProcessor<Archive, Archive>() {

			@Override
			public Archive process(Archive item) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	@Bean
    public ItemReader<Archive> reader() {
        FlatFileItemReader<Archive> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(inputDirectory));
        reader.setLineMapper(new ArchiveJsonLineMapper());
        return reader;
    }
	
	@Bean
	public FlatFileItemWriter<Archive> writer() {
		FlatFileItemWriter<Archive> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource(outputDirectory));
		writer.setAppendAllowed(true);
		writer.setSaveState(true);
		writer.open(new ExecutionContext());
		writer.setLineAggregator(new ArchiveJsonItemAggregator());
		return writer;
	}
	
}
