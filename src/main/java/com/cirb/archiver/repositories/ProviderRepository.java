package com.cirb.archiver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirb.archiver.domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

}
