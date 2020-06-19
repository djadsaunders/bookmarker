package com.djad.bookmarker.webservice;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.djad.bookmarker.dto.BookmarkDTO;
import com.djad.bookmarker.service.BookmarkService;
import com.djad.bookmarker.service.FaviconService;
import com.djad.bookmarker.FileUtils;

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
        return bookmarkService.getCurrentUserName();
    }
    
    @GetMapping(value="/create")
    public RedirectView createBookmark(@RequestParam("url") String url, HttpServletRequest request) {
        String fileName = FileUtils.writeFaviconFile(faviconService.getFaviconAsByteArray(url));
        bookmarkService.createBookmark(this.getCurrentUserId(), url, fileName);
        return new RedirectView("/index.html");
    }

    @GetMapping
    public Optional<BookmarkDTO> getPendingBookmark() {
        log.debug("Get pending bookmark");
        return bookmarkService.getPendingBookmark();
    }

}
