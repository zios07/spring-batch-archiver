package com.cirb.archiver.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirb.archiver.domain.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

	List<Consumer> findByExternalTimestampLessThanEqualOrderById(Date date);

}
