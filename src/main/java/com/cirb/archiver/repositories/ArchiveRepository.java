package com.cirb.archiver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirb.archiver.domain.Archive;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

}
