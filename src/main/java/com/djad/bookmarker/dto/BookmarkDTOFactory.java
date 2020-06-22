package com.djad.bookmarker.dto;

import java.util.ArrayList;
import java.util.List;

import com.djad.bookmarker.domain.Bookmark;
import com.djad.bookmarker.service.FaviconStorageHandler;

import org.springframework.stereotype.Component;

@Component
public class BookmarkDTOFactory {

    private FaviconStorageHandler faviconStorageHandler;

    public BookmarkDTOFactory(FaviconStorageHandler faviconStorageHandler) {
        this.faviconStorageHandler = faviconStorageHandler;
    }

    public List<BookmarkDTO> createDTOsFromList(List<Bookmark> bookmarks) {
        List<BookmarkDTO> dtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            // Don't include pending bookmarks
            if (!bookmark.isPending()) {
                dtos.add(this.createDTO(bookmark));
            }
        }
        return dtos;
    }
    
    public BookmarkDTO createDTO(Bookmark bookmark) {
        byte[] favicon = faviconStorageHandler.readFile(bookmark.getFaviconFile());
        return new BookmarkDTO(
            bookmark.getCategory().getName(), 
            bookmark.getId(), 
            bookmark.getName(), 
            bookmark.getUrl(),
            favicon
        );
    } 
}