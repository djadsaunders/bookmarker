package com.djad.bookmarker.service;

import java.util.List;

import javax.transaction.Transactional;

import com.djad.bookmarker.domain.Category;
import com.djad.bookmarker.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Category> getAllCategories() {
        return (List<Category>)categoryRepository.findAll();
    }
}