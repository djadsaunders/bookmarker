package com.djad.bookmarker.webservice;

import java.util.ArrayList;
import java.util.List;

import com.djad.bookmarker.domain.Category;
import com.djad.bookmarker.dto.BookmarkDTO;
import com.djad.bookmarker.dto.BookmarkDTOFactory;
import com.djad.bookmarker.dto.CategoryDTO;
import com.djad.bookmarker.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/category")
public class CategoryController {

    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryDTO> listCategories() {
        List<Category> categories = service.getAllCategories();
        List<CategoryDTO> dtos = new ArrayList<>();
        for (Category category : categories) {
            List<BookmarkDTO> bookmarks = BookmarkDTOFactory.createBookmarkDTOs(category.getBookmarks());
            dtos.add(new CategoryDTO(category.getName(), bookmarks));
        }
        
        return dtos;
    }
}
