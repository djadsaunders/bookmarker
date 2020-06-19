package com.djad.bookmarker.dto;

import java.util.ArrayList;
import java.util.List;

import com.djad.bookmarker.FileUtils;
import com.djad.bookmarker.domain.Bookmark;

public class BookmarkDTOFactory {

    public static List<BookmarkDTO> createDTOsFromList(List<Bookmark> bookmarks) {
        List<BookmarkDTO> dtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            // Don't include pending bookmarks
            if (!bookmark.isPending()) {
                dtos.add(BookmarkDTOFactory.createDTO(bookmark));
            }
        }
        return dtos;
    }    
    
    public static BookmarkDTO createDTO(Bookmark bookmark) {
        byte[] favicon = FileUtils.readFaviconFile(bookmark.getFaviconFile());
        return new BookmarkDTO(
            bookmark.getCategory().getName(), 
            bookmark.getId(), 
            bookmark.getName(), 
            bookmark.getUrl(),
            favicon
        );
    } 
}