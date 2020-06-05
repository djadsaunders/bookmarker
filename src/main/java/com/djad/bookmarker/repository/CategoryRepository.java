package com.djad.bookmarker.repository;

import java.util.Optional;

import com.djad.bookmarker.domain.Category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
