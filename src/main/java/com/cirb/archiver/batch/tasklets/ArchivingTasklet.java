package com.cirb.archiver.batch.tasklets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.cirb.archiver.domain.JsonArchive;
import com.cirb.archiver.domain.Consumer;
import com.cirb.archiver.domain.Provider;
import com.cirb.archiver.repositories.ConsumerRepository;
import com.cirb.archiver.repositories.ProviderRepository;

public class ArchivingTasklet implements Tasklet {

	private ConsumerRepository consumerRepository;
	
	private ProviderRepository providerRepository;
	
	private ItemWriter<JsonArchive> itemWriter;

	@Autowired
	public ArchivingTasklet(ConsumerRepository consumerRepository, ProviderRepository providerRepository, ItemWriter<JsonArchive> itemWriter) {
		this.consumerRepository = consumerRepository;
		this.providerRepository = providerRepository;
		this.itemWriter = itemWriter;
	}
	
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		Calendar oneYearAgo = Calendar.getInstance();
		oneYearAgo.set(oneYearAgo.get(Calendar.YEAR) - 1, oneYearAgo.get(Calendar.MONTH), oneYearAgo.get(Calendar.DATE));
		List<Consumer> consumers = consumerRepository.findByExternalTimestampLessThanEqual(oneYearAgo.getTime());
		List<Provider> providers = providerRepository.findByExternalTimestampLessThanEqual(oneYearAgo.getTime());
		// TODO remove the counter
		int i  = 1;
		List<JsonArchive> archives = new ArrayList<>();
		
		for(Consumer consumer : consumers) {
			for(Provider provider : providers) {
				if(consumer.getTransactionId() != null && consumer.getTransactionId().equals(provider.getTransactionId())) {
					JsonArchive archive = new JsonArchive(new Date(), consumer, provider);
					archives.add(archive);
					i++;
				}
				if( i == 3 )
					break;
			}
		}
		this.itemWriter.write(archives);
		return RepeatStatus.FINISHED;
	}

}
