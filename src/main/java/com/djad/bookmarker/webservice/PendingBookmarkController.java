package com.djad.bookmarker.webservice;

import java.util.Optional;

import com.djad.bookmarker.dto.BookmarkDTO;
import com.djad.bookmarker.service.BookmarkService;
import com.djad.bookmarker.service.FaviconService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/pending-bookmark")
@SessionAttributes("userId")
@Slf4j
public class PendingBookmarkController {

    private BookmarkService bookmarkService;

    private FaviconService faviconService;
   
    @Autowired
    public PendingBookmarkController(BookmarkService bookmarkService, FaviconService faviconService) {
        this.bookmarkService = bookmarkService;
        this.faviconService = faviconService;
    }

    @ModelAttribute("userId")
    public String getCurrentUserId() {
        log.debug("Get current user ID");
        return bookmarkService.getCurrentUserName();
    }
    
    @GetMapping(value="/create")
    public RedirectView createBookmark(@RequestParam("url") String url) {
        log.debug("Create bookmark");
        String faviconName = this.faviconService.getAndStoreFavicon(url);
        bookmarkService.createBookmark(this.getCurrentUserId(), url, faviconName);
        return new RedirectView("/index.html");
    }

    @GetMapping
    public Optional<BookmarkDTO> getPendingBookmark() {
        log.debug("Get pending bookmark");
        return bookmarkService.getPendingBookmark();
    }

}
