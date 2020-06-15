package com.djad.bookmarker.service;

import com.djad.bookmarker.domain.Category;
import com.djad.bookmarker.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BootstrapService {
    
    @Autowired
    public CategoryRepository categoryRepository;

    public void seedData() {
        categoryRepository.save(new Category("dan", Category.DEFAULT_NAME));
        categoryRepository.save(new Category("ali", Category.DEFAULT_NAME));
    }
}