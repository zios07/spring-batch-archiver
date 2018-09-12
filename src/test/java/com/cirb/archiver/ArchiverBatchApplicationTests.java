package com.cirb.archiver;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cirb.archiver.domain.Archive;
import com.cirb.archiver.domain.Consumer;
import com.cirb.archiver.domain.Provider;
import com.cirb.archiver.repositories.ArchiveRepository;
import com.cirb.archiver.repositories.ConsumerRepository;
import com.cirb.archiver.repositories.ProviderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArchiverBatchApplicationTests {

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private ProviderRepository providerRepository;

	@Autowired
	private ArchiveRepository archiveRepository;

	@Test
	public void contextLoads() {
		Consumer consumer = new Consumer("aaa", "aa", "aaaa", ",bbbb", "bbbbb", "zeazeaze", "eazeae", "pjarlejk",
				"opiezrpzj", "oijjn", "aezlkaje", "amezkzae", "aaa");
		Provider provider = new Provider("amezkzae", "amzekazmek", "aaa", "aa", "aaaa", ",bbbb", "bbbbb", "zeazeaze",
				"eazeae", "pjarlejk", "opiezrpzj", "oijjn", "aaa");

		
		consumer = this.consumerRepository.save(consumer);
		provider = this.providerRepository.save(provider);
		

		Archive archive = new Archive(new Date(), consumer, provider);
		this.archiveRepository.save(archive);
		
		assertEquals(1, 1);
	}

}
