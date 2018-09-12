package com.cirb.archiver.batch.tasklets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.cirb.archiver.domain.Archive;
import com.cirb.archiver.domain.Consumer;
import com.cirb.archiver.domain.Provider;
import com.cirb.archiver.repositories.ConsumerRepository;
import com.cirb.archiver.repositories.ProviderRepository;

public class PostgresToCsv implements Tasklet {

	private ConsumerRepository consumerRepository;
	
	private ProviderRepository providerRepository;
	
	private ItemWriter<Archive> itemWriter;

	@Autowired
	public PostgresToCsv(ConsumerRepository consumerRepository, ProviderRepository providerRepository, ItemWriter<Archive> itemWriter) {
		this.consumerRepository = consumerRepository;
		this.providerRepository = providerRepository;
		this.itemWriter = itemWriter;
	}
	
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		List<Consumer> consumers = consumerRepository.findAll();
		List<Provider> providers = providerRepository.findAll();
		List<Archive> archives = new ArrayList<>();
		for(Consumer consumer : consumers) {
			for(Provider provider : providers) {
				// consumer and provider of the same transaction
				if(consumer.getTransactionId() != null && consumer.getTransactionId().equals(provider.getTransactionId())) {
					Archive archive = new Archive(new Date(), consumer, provider);
					archives.add(archive);
				}
			}
		}
		
		this.itemWriter.write(archives);
		return RepeatStatus.FINISHED;
	}

}
