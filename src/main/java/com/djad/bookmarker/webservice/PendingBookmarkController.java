package com.djad.bookmarker.webservice;

import java.util.Optional;

import com.djad.bookmarker.domain.Bookmark;
import com.djad.bookmarker.dto.BookmarkDTO;
import com.djad.bookmarker.dto.BookmarkDTOFactory;
import com.djad.bookmarker.service.BookmarkService;
import com.djad.bookmarker.service.FaviconService;

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

    private BookmarkService bookmarkService;

    private FaviconService faviconService;

    @Autowired
    public PendingBookmarkController(BookmarkService bookmarkService, FaviconService faviconService) {
        this.bookmarkService = bookmarkService;
        this.faviconService = faviconService;
    }

    @GetMapping(value="/create")
    public RedirectView createBookmark(@RequestParam("url") String url) {
        byte[] faviconImage = faviconService.getFaviconAsByteArray(url);        
        bookmarkService.createPendingBookmark(url, faviconImage);
        return new RedirectView("/index.html");
    }

    @GetMapping
    public Optional<BookmarkDTO> getPendingBookmark() {
        logger.debug("Get pending bookmark");
        return bookmarkService.getPendingBookmark();
    }
}
