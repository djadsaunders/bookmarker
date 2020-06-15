package com.djad.bookmarker.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.djad.bookmarker.domain.Category;
import com.djad.bookmarker.dto.BookmarkDTO;
import com.djad.bookmarker.dto.BookmarkDTOFactory;
import com.djad.bookmarker.dto.CategoryDTO;
import com.djad.bookmarker.repository.CategoryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService {

    Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<CategoryDTO> getAllCategories() {
        logger.debug("Get all categories");

        this.setUserFilter();

        List<Category> categories = (List<Category>)categoryRepository.findAll();  
        List<CategoryDTO> dtos = new ArrayList<>();
        for (Category category : categories) {
            List<BookmarkDTO> bookmarks = BookmarkDTOFactory.createDTOsFromList(category.getBookmarks());
            dtos.add(new CategoryDTO(category.getId(), category.getName(), bookmarks));
        }        
       
        return dtos;
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