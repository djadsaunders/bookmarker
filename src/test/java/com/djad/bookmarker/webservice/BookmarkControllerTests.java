package com.djad.bookmarker.webservice;

import static org.mockito.Mockito.verify;

import com.djad.bookmarker.dto.BookmarkDTOFactory;
import com.djad.bookmarker.service.BookmarkService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BookmarkControllerTests {

    @InjectMocks
    BookmarkController bookmarkController;

    @Mock
    BookmarkService bookmarkService;

    @Mock
    BookmarkDTOFactory bookmarkDTOFactory;

    @Test
    public void testGetCurrentUserId() {
        bookmarkController.getCurrentUserId();
        verify(bookmarkService).getCurrentUserName();
    }

    @Test
    public void testUpdateBookmark() {
        bookmarkController.updateBookmark(1, "test-bookmark", "test-category");
        verify(bookmarkService).updateBookmark(1, "test-bookmark", "test-category");
    }

    @Test
    public void testListBookmarks() {
        bookmarkController.listBookmarks();
        verify(bookmarkService).getAllBookmarks();
    }

    @Test
    public void testDeleteBookmark() {
        bookmarkController.deleteBookmark(1);
        verify(bookmarkService).deleteBookmark(1);
    }
}