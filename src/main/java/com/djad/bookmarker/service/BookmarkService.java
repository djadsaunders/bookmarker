package com.djad.bookmarker.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.djad.bookmarker.domain.Bookmark;
import com.djad.bookmarker.domain.Category;
import com.djad.bookmarker.dto.BookmarkDTO;
import com.djad.bookmarker.dto.BookmarkDTOFactory;
import com.djad.bookmarker.repository.BookmarkRepository;
import com.djad.bookmarker.repository.CategoryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService extends AbstractService {

    Logger logger = LoggerFactory.getLogger(BookmarkService.class);

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired 
    private BookmarkDTOFactory bookmarkDTOFactory;

    @Transactional
    public Bookmark createBookmark(String userId, String url, String faviconFile) {
        logger.debug("Create bookmark");

        // Look for bookmark first and return if already existing (based on URL)
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByUrlAndUserId(url, userId);
        if (existingBookmark.isPresent()) {
            return existingBookmark.get();
        }

        // Add to the default category, create if it doesn't exist
        Optional<Category> defaultCategory = categoryRepository.findByNameAndUserId(Category.DEFAULT_NAME, userId);
        if (!defaultCategory.isPresent()) {
            defaultCategory = Optional.of(new Category(userId, Category.DEFAULT_NAME));
        }

        Bookmark bookmark = bookmarkRepository.save(
            new Bookmark(userId, defaultCategory.get(), url, faviconFile));

        return bookmark;
    }

    @Transactional
    public List<Bookmark> getAllBookmarks() {
        logger.debug("Get all bookmarks");

        this.setUserFilter();

        return bookmarkRepository.findByPendingAndUserId(false, this.getCurrentUserName());
    }

    @Transactional
    public Optional<BookmarkDTO> getPendingBookmark() {
        logger.debug("Get pending bookmark");

        this.setUserFilter();

        List<Bookmark> bookmarks = bookmarkRepository.findByPendingAndUserId(true, this.getCurrentUserName());
        if (bookmarks.size() == 0) {
            return Optional.empty();
        }
        
        return Optional.of(bookmarkDTOFactory.createDTO(bookmarks.get(0)));
    }

    @Transactional
    public void updateBookmark(long id, String name, String categoryName) {
        logger.debug("Update bookmark");

        Bookmark bookmark = bookmarkRepository.findById(id).get();

        // May be updating with a new category in which case, add it
        Optional<Category> category = categoryRepository.findByNameAndUserId(categoryName, bookmark.getUserId());
        if (!category.isPresent()) {
            category = Optional.of(new Category(bookmark.getUserId(), categoryName));
        }

        // Update and save
        bookmark.setName(name);
        bookmark.setCategory(category.get());
        bookmark.setPending(false);
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void deleteBookmark(long id) {
        logger.debug("Delete bookmark");
        bookmarkRepository.deleteById(id);
    }
}