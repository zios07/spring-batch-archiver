/**
 * 
 */
package com.cirb.archiver.batch.tasklets;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
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

import com.cirb.archiver.batch.utils.JavaPGP;
import com.cirb.archiver.domain.Consumer;
import com.cirb.archiver.domain.JsonArchive;
import com.cirb.archiver.domain.Provider;
import com.cirb.archiver.repositories.ConsumerRepository;
import com.cirb.archiver.repositories.ProviderRepository;

/**
 * @author Zkaoukab
 *
 */
public class FieldsEncryptionStep implements Tasklet {

	private ConsumerRepository consumerRepository;

	private ProviderRepository providerRepository;

	private ItemWriter<JsonArchive> itemWriter;

	private JavaPGP encryptor;

	@Autowired
	public FieldsEncryptionStep(ConsumerRepository consumerRepository, ProviderRepository providerRepository, ItemWriter<JsonArchive> itemWriter) throws NoSuchAlgorithmException {
		this.consumerRepository = consumerRepository;
		this.providerRepository = providerRepository;
		this.itemWriter = itemWriter;
		encryptor = JavaPGP.getInstance();
	}

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		Calendar oneYearAgo = Calendar.getInstance();
		oneYearAgo.set(oneYearAgo.get(Calendar.YEAR) - 1, oneYearAgo.get(Calendar.MONTH), oneYearAgo.get(Calendar.DATE));
		List<Consumer> consumers = consumerRepository.findByExternalTimestampLessThanEqual(oneYearAgo.getTime());
		List<Provider> providers = providerRepository.findByExternalTimestampLessThanEqual(oneYearAgo.getTime());
		// TODO remove the counter
		int i = 1;
		List<JsonArchive> archives = new ArrayList<>();

		for (Consumer consumer : consumers) {
			for (Provider provider : providers) {
				if (consumer.getTransactionId() != null && consumer.getTransactionId().equals(provider.getTransactionId())) {
					JsonArchive archive = new JsonArchive(new Date(), encryptConsumerFields(consumer), encryptProviderFields(provider));
					archives.add(archive);
					i++;
				}
				if (i == 3)
					break;
			}
		}
		this.itemWriter.write(archives);
		return RepeatStatus.FINISHED;
	}

	private Consumer encryptConsumerFields(Consumer consumer) throws UnsupportedEncodingException, GeneralSecurityException {
		consumer.setInstitute(encryptor.encrypt(consumer.getInstitute()));
		consumer.setLegalContext(encryptor.encrypt(consumer.getLegalContext()));
		return consumer;
	}

	private Provider encryptProviderFields(Provider provider) throws UnsupportedEncodingException, GeneralSecurityException {
		provider.setInstitute(encryptor.encrypt(provider.getInstitute()));
		provider.setLegalContext(encryptor.encrypt(provider.getLegalContext()));
		return provider;
	}

}
