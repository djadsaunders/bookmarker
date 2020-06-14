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
public class BookmarkService {

    Logger logger = LoggerFactory.getLogger(BookmarkService.class);

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public BookmarkDTO createPendingBookmark(String url, byte[] faviconBytes) {
        return this.createBookmark(url, null, true, faviconBytes);
    }

    @Transactional
    public BookmarkDTO createBookmark(String url, String name, boolean pending, byte[] faviconBytes) {
        logger.debug("Create bookmark");

        // Look for bookmark first and return if already existing (based on URL)
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByUrl(url);
        if (existingBookmark.isPresent()) {
            return BookmarkDTOFactory.createDTO(existingBookmark.get());
        }

        // Add to the default category, create if it doesn't exist
        Optional<Category> defaultCategory = categoryRepository.findByName(Category.DEFAULT_NAME);
        if (!defaultCategory.isPresent()) {
            defaultCategory = Optional.of(new Category(Category.DEFAULT_NAME));
        }

        Bookmark bookmark = bookmarkRepository.save(new Bookmark(defaultCategory.get(), url, name, pending, faviconBytes));

        return BookmarkDTOFactory.createDTO(bookmark);
    }

    @Transactional
    public List<BookmarkDTO> getAllBookmarks() {
        logger.debug("Get all bookmarks");
        List<Bookmark> bookmarks = bookmarkRepository.findByPending(false);
        return BookmarkDTOFactory.createDTOsFromList(bookmarks);
    }

    @Transactional
    public Optional<BookmarkDTO> getPendingBookmark() {
        logger.debug("Get pending bookmark");
        List<Bookmark> bookmarks = bookmarkRepository.findByPending(true);
        if (bookmarks.size() == 0) {
            return Optional.empty();
        }
        
        return Optional.of(BookmarkDTOFactory.createDTO(bookmarks.get(0)));
    }

    @Transactional
    public void updateBookmark(long id, String name, String categoryName) {
        logger.debug("Update bookmark");

        Bookmark bookmark = bookmarkRepository.findById(id).get();
        //Category oldCategory = bookmark.getCategory();

        // May be updating with a new category in which case, add it
        Optional<Category> category = categoryRepository.findByName(categoryName);
        if (!category.isPresent()) {
            category = Optional.of(new Category(categoryName));
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