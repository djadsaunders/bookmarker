package com.djad.bookmarker.service;

import java.util.Optional;

import com.djad.bookmarker.domain.Category;
import com.djad.bookmarker.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BootstrapService {
    
    @Autowired
    public CategoryRepository categoryRepository;

    public void seedData() {

        // Add default categories
        Optional<Category> cat1 = categoryRepository.findByNameAndUserId(Category.DEFAULT_NAME, "dan");
        Optional<Category> cat2 = categoryRepository.findByNameAndUserId(Category.DEFAULT_NAME, "ali");

        if (!cat1.isPresent()) {
            categoryRepository.save(new Category("dan", Category.DEFAULT_NAME));
        }
        if (!cat2.isPresent()) {
            categoryRepository.save(new Category("ali", Category.DEFAULT_NAME));
        }
    }
}