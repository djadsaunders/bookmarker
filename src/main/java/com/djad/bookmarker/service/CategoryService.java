package com.djad.bookmarker.service;

import java.util.List;

import javax.transaction.Transactional;

import com.djad.bookmarker.domain.Category;
import com.djad.bookmarker.repository.CategoryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    Logger logger = LoggerFactory.getLogger(BookmarkService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Category> getAllCategories() {
        return (List<Category>)categoryRepository.findAll();
    }

    @Transactional
    public void updateCategory(long id, String name) {
        logger.debug("Update category");

        Category category = categoryRepository.findById(id).get();
        category.setName(name);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(long id) {
        logger.debug("Delete category");
        categoryRepository.deleteById(id);
    }    
}