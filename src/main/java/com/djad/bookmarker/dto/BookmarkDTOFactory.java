package com.djad.bookmarker.dto;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import com.djad.bookmarker.ApplicationException;
import com.djad.bookmarker.domain.Bookmark;

public class BookmarkDTOFactory {

    private static byte[] blobToByteArray(Blob blob) {
        byte[] result = null;

        try {
            int blobLength = (int) blob.length();  
            result = blob.getBytes(1, blobLength);
        }
        catch (Exception e) {
            throw new ApplicationException("Failed to create byte[] for favicon", e);
        }

        return result;
    }

    public static List<BookmarkDTO> createDTOsFromList(List<Bookmark> bookmarks) {
        List<BookmarkDTO> dtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            // Don't include pending bookmarks
            if (!bookmark.isPending()) {
                byte[] favicon = blobToByteArray(bookmark.getFavicon());
                dtos.add(new BookmarkDTO(
                    bookmark.getCategory().getName(), 
                    bookmark.getId(), 
                    bookmark.getName(), 
                    bookmark.getUrl(),
                    favicon
                ));
            }
        }
        return dtos;
    }    
    
    public static BookmarkDTO createDTO(Bookmark bookmark) {
        byte[] favicon = blobToByteArray(bookmark.getFavicon());
        return new BookmarkDTO(
            bookmark.getCategory().getName(), 
            bookmark.getId(), 
            bookmark.getName(), 
            bookmark.getUrl(),
            favicon
        );
    } 
}