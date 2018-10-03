package com.cirb.archiver.repositories;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.cirb.archiver.domain.SolrArchive;

public interface SolrArchiveRepository extends SolrCrudRepository<SolrArchive, Long> {

}
