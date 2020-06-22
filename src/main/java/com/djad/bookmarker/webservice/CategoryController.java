package com.djad.bookmarker.webservice;

import java.util.ArrayList;
import java.util.List;

import com.djad.bookmarker.domain.Category;
import com.djad.bookmarker.dto.BookmarkDTO;
import com.djad.bookmarker.dto.BookmarkDTOFactory;
import com.djad.bookmarker.dto.CategoryDTO;
import com.djad.bookmarker.service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController
@RequestMapping(value="/category")
@SessionAttributes("userId")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;

    private BookmarkDTOFactory bookmarkDTOFactory;

    @ModelAttribute("userId")
    public String getCurrentUserId() {
        return categoryService.getCurrentUserName();
    }
    
    @Autowired
    public CategoryController(CategoryService categoryService, BookmarkDTOFactory bookmarkDTOFactory) {
        this.categoryService = categoryService;
        this.bookmarkDTOFactory = bookmarkDTOFactory;
    }

    @PostMapping("/{id}")
    public void updateCategory(@PathVariable long id, @RequestParam("categoryName") String name) {
        logger.debug("Update category: name=" + name + ", user=" + this.getCurrentUserId());
        categoryService.updateCategory(id, name);
    }

    @GetMapping
    public List<CategoryDTO> listCategories() {
        logger.debug("List categories, user=" + this.getCurrentUserId());

        List<Category> categories = categoryService.getAllCategories();  
        List<CategoryDTO> dtos = new ArrayList<>();
        for (Category category : categories) {
            List<BookmarkDTO> bookmarks = bookmarkDTOFactory.createDTOsFromList(category.getBookmarks());
            dtos.add(new CategoryDTO(category.getId(), category.getName(), bookmarks));
        }        

        return dtos;
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        logger.debug("Delete category, user=" + this.getCurrentUserId());
        categoryService.deleteCategory(id);
    }
}
