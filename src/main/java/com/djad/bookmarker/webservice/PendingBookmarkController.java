package com.djad.bookmarker.webservice;

import java.util.Optional;

import com.djad.bookmarker.domain.Bookmark;
import com.djad.bookmarker.dto.BookmarkDTO;
import com.djad.bookmarker.service.BookmarkService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value="/pending-bookmark")
public class PendingBookmarkController {

    Logger logger = LoggerFactory.getLogger(PendingBookmarkController.class);

    private BookmarkService service;

    @Autowired
    public PendingBookmarkController(BookmarkService service) {
        this.service = service;
    }

    @GetMapping(value="/create")
    public RedirectView createBookmark(@RequestParam("url") String url) {
        service.createPendingBookmark(url);
        return new RedirectView("/index.html");
    }

    @GetMapping
    public Optional<BookmarkDTO> getPendingBookmark() {
        logger.debug("Get pending bookmark");
        BookmarkDTO dto = null;
        Optional<Bookmark> bookmark = service.getPendingBookmark();
        if (bookmark.isPresent()) {
            dto = new BookmarkDTO(bookmark.get().getId(), bookmark.get().getName(), bookmark.get().getUrl());
        }
        return Optional.ofNullable(dto);
    }
}
