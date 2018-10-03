package com.cirb.archiver.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

@Configuration
public class SolrConfig {

	@Value("${solr.host}")
	private String solrHost;
	
	@Value("${solr.port}")
	private int solrPort;
	
	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient.Builder("http://" + solrHost + ":" + solrPort).build();
	}
	
	@Bean
    public SolrTemplate solrTemplate() throws Exception {
        return new SolrTemplate(solrClient());
    }

}
