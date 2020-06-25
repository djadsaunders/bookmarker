package com.djad.bookmarker.repository;

import java.util.Optional;

import com.djad.bookmarker.domain.Favicon;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaviconRepository extends CrudRepository<Favicon, Long> {
    Optional<Favicon> findByName(String name);
}
