package com.cirb.archiver.batch.jobs;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.cirb.archiver.batch.tasklets.FieldsEncryptionTasklet;
import com.cirb.archiver.batch.tasklets.SolrTasklet;
import com.cirb.archiver.batch.utils.ArchiveJsonItemAggregator;
import com.cirb.archiver.domain.JsonArchive;
import com.cirb.archiver.repositories.ConsumerRepository;
import com.cirb.archiver.repositories.ProviderRepository;
import com.cirb.archiver.repositories.SolrArchiveRepository;

@Configuration
public class ArchivingJob {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${batch.archive-directory}")
    private String archiveDirectory;

    @Value("${jks.password}")
    private String jksPassword;

    @Value("${jks.path}")
    private String jksFilepath;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private SolrArchiveRepository solrArchiveRepository;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job archiverJob() throws NoSuchAlgorithmException {
        return jobBuilderFactory.get("archivingJob")
                .incrementer(new RunIdIncrementer())
                .start(fieldsEncryptionStep())
				.next(solrStep())
                .build();
    }

    // Steps config

    @Bean
    protected Step fieldsEncryptionStep() throws NoSuchAlgorithmException {
        return stepBuilderFactory.get("fieldsEncryptionStep").tasklet(fieldsEncryptionTasklet()).build();
    }

    @Bean
    protected Step solrStep() {
        return stepBuilderFactory.get("solrStep").tasklet(solrTasklet(solrArchiveRepository)).build();
    }

    // Tasklets config

    @Bean
    protected Tasklet fieldsEncryptionTasklet() throws NoSuchAlgorithmException {
        return new FieldsEncryptionTasklet(consumerRepository, providerRepository, writer(), jksPassword, jksFilepath);
    }

    @Bean
    protected Tasklet solrTasklet(SolrArchiveRepository solrArchiveRepository) {
        return new SolrTasklet(solrArchiveRepository, archiveDirectory);
    }

    // ItemWriter config

    @Bean
    @StepScope
    public FlatFileItemWriter<JsonArchive> writer() {
        FlatFileItemWriter<JsonArchive> writer = new FlatFileItemWriter<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());
        writer.setResource(new FileSystemResource(archiveDirectory + "archive_" + date + ".json"));
        writer.setSaveState(true);
        writer.open(new ExecutionContext());
        writer.setLineAggregator(new ArchiveJsonItemAggregator());
        return writer;
    }

}
