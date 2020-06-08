package com.djad.bookmarker.dto;

import java.util.ArrayList;
import java.util.List;

import com.djad.bookmarker.domain.Bookmark;

public class BookmarkDTOFactory {

    public static List<BookmarkDTO> createBookmarkDTOs(List<Bookmark> bookmarks) {
        List<BookmarkDTO> dtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            // Don't include pending bookmarks
            if (!bookmark.isPending()) {
                dtos.add(new BookmarkDTO(
                    bookmark.getCategory().getName(), bookmark.getId(), bookmark.getName(), 
                        bookmark.getUrl()));
            }
        }
        return dtos;
    }    
    
}