package com.djad.bookmarker.service;

import com.djad.bookmarker.domain.Bookmark;
import com.djad.bookmarker.domain.Category;
import com.djad.bookmarker.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BootstrapService {
    
    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public FaviconService faviconService;

    public void seedData() {
        categoryRepository.save(new Category("dan", Category.DEFAULT_NAME));
        categoryRepository.save(new Category("ali", Category.DEFAULT_NAME));
        createDefaultBookmarks("dan");
        createDefaultBookmarks("ali");
    }

    private void createDefaultBookmarks(String user) {
        Category catShopping = new Category(user, "Shopping");
        Category catServices = new Category(user, "Services");

        createBookmark(user, catShopping, "https://www.amazon.co.uk/", "Amazon");
        createBookmark(user, catServices, "https://mail.google.com/mail/u/0/#inbox", "GMail");
        
        categoryRepository.save(catShopping);
        categoryRepository.save(catServices);
    }

    private void createBookmark(String user, Category category, String url, String name) {
        Bookmark bookmark = 
            new Bookmark(user, category, url, name, false, faviconService.getAndStoreFavicon(url));
        category.getBookmarks().add(bookmark);
    }
}