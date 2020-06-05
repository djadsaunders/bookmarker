package com.djad.bookmarker.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.djad.bookmarker.domain.Bookmark;
import com.djad.bookmarker.domain.Category;
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
    public Bookmark createPendingBookmark(String url) {
        return this.createBookmark(url, null, true);
    }

    @Transactional
    public Bookmark createBookmark(String name, String url) {
        return this.createBookmark(url, name, false);
    }

    @Transactional
    public Bookmark createBookmark(String url, String name, boolean pending) {
        logger.debug("Create bookmark");

        // Look for bookmark first and return if already existing (based on URL)
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByUrl(url);
        if (existingBookmark.isPresent()) {
            return existingBookmark.get();
        }

        Optional<Category> defaultCategory = categoryRepository.findByName(Category.DEFAULT_NAME);
        return bookmarkRepository.save(new Bookmark(defaultCategory.get(), url, name, pending));
    }

    @Transactional
    public List<Bookmark> getAllBookmarks() {
        logger.debug("Get all bookmarks");
        return (List<Bookmark>)bookmarkRepository.findByPending(false);
    }

    @Transactional
    public Optional<Bookmark> getPendingBookmark() {
        logger.debug("Get pending bookmark");
        List<Bookmark> bookmarks = bookmarkRepository.findByPending(true);
        if (bookmarks.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(bookmarks.get(0));
    }

    @Transactional
    public void updateBookmark(long id, String name) {
        logger.debug("Convert pending bookmark to real");
        Bookmark bookmark = bookmarkRepository.findById(id).get();
        bookmark.setName(name);
        bookmark.setPending(false);
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void deleteBookmark(long id) {
        logger.debug("Delete bookmark");
        bookmarkRepository.deleteById(id);
    }
}