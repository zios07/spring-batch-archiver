package com.cirb.archiver;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cirb.archiver.domain.Consumer;
import com.cirb.archiver.domain.Provider;
import com.cirb.archiver.repositories.ConsumerRepository;
import com.cirb.archiver.repositories.ProviderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArchiverBatchApplicationTests {

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private ProviderRepository providerRepository;

	@Test
	public void contextLoads() {
		Consumer consumer = new Consumer("aaa", "aa", "aaaa", ",bbbb", "bbbbb", "zeazeaze", "eazeae", "pjarlejk",
				"opiezrpzj", "oijjn", "aezlkaje", "amezkzae", "aaa", new Date());
		Provider provider = new Provider("amezkzae", "amzekazmek", "aaa", "aa", "aaaa", ",bbbb", "bbbbb", "zeazeaze",
				"eazeae", "pjarlejk", "opiezrpzj", "oijjn", "aaa", new Date());

		Calendar oneYearAgo = Calendar.getInstance();
		oneYearAgo.set(oneYearAgo.get(Calendar.YEAR) - 2, oneYearAgo.get(Calendar.MONTH),
				oneYearAgo.get(Calendar.DATE));

		Consumer consumer2 = new Consumer("aaa", "aa", "aaaa", ",bbbb", "bbbbb", "zeazeaze", "eazeae", "pjarlejk",
				"opiezrpzj", "oijjn", "aezlkaje", "amezkzae", "xxxx", oneYearAgo.getTime());
		Provider provider2 = new Provider("amezkzae", "amzekazmek", "aaa", "aa", "aaaa", ",bbbb", "bbbbb", "zeazeaze",
				"eazeae", "pjarlejk", "opiezrpzj", "oijjn", "xxxx", oneYearAgo.getTime());

		consumer = this.consumerRepository.save(consumer);
		provider = this.providerRepository.save(provider);

		consumer2 = this.consumerRepository.save(consumer2);
		provider2 = this.providerRepository.save(provider2);

		assertEquals(1, 1);
	}

}
