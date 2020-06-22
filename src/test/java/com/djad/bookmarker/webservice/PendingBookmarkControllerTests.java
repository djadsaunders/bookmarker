package com.djad.bookmarker.webservice;

import static org.mockito.Mockito.verify;

import com.djad.bookmarker.service.BookmarkService;
import com.djad.bookmarker.service.FaviconService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class PendingBookmarkControllerTests {

    PendingBookmarkController pendingBookmarkController;

    @Mock
    BookmarkService bookmarkService;

    @Mock
    FaviconService faviconService;

    @Before
    public void setup() {
        pendingBookmarkController = new PendingBookmarkController(bookmarkService, faviconService);
    }

    @Test
    public void testGetCurrentUserId() {
        pendingBookmarkController.getCurrentUserId();
        verify(bookmarkService).getCurrentUserName();
    }

    @Test
    public void testCreateBookmark() {
        log.debug("Create bookmark");
        pendingBookmarkController.createBookmark("url");
        verify(faviconService).getAndStoreFavicon("url");
    }

    @Test
    public void testGetPendingBookmark() {
        pendingBookmarkController.getPendingBookmark();
        verify(bookmarkService).getPendingBookmark();
    }
}