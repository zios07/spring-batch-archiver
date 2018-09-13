package com.cirb.archiver.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirb.archiver.domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

	List<Provider> findByExternalTimestampLessThanEqual(Date date);
	
}
