package com.djad.bookmarker.repository;

import java.util.List;
import java.util.Optional;

import com.djad.bookmarker.domain.Bookmark;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    Optional<Bookmark> findByName(String name);
    Optional<Bookmark> findByUrl(String name);
    List<Bookmark> findByPending(boolean pending);
}
