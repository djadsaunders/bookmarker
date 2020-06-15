package com.djad.bookmarker.repository;

import java.util.List;
import java.util.Optional;

import com.djad.bookmarker.domain.Bookmark;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    Optional<Bookmark> findByUrlAndUserId(String name, String userId);
    List<Bookmark> findByPendingAndUserId(boolean pending, String userId);
}
