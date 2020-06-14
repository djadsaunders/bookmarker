package com.djad.bookmarker.webservice;

import java.util.List;

import com.djad.bookmarker.dto.BookmarkDTO;
import com.djad.bookmarker.service.BookmarkService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/bookmark")
public class BookmarkController {

    Logger logger = LoggerFactory.getLogger(BookmarkController.class);

    private BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/{id}")
    public void updateBookmark(@PathVariable long id, @RequestParam("bookmarkName") String name,
            @RequestParam("categoryName") String category) {
        logger.debug("Update bookmark: name=" + name + ", category=" + category);
        bookmarkService.updateBookmark(id, name, category);
    }

    @GetMapping
    public List<BookmarkDTO> listBookmarks() {
        logger.debug("List bookmarks");
        return bookmarkService.getAllBookmarks();
        //return BookmarkDTOFactory.createDTOsFromList(bookmarkService.getAllBookmarks());
    }

    @DeleteMapping("/{id}")
    public void deleteBookmark(@PathVariable long id) {
        logger.debug("Delete bookmark");
        bookmarkService.deleteBookmark(id);
    }
}
