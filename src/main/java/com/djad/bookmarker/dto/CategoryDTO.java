package com.djad.bookmarker.dto;

import java.util.List;

public class CategoryDTO {
    private String name;
    private List<BookmarkDTO> bookmarks;

    public CategoryDTO(String name, List<BookmarkDTO> bookmarks) {
        this.name = name;
        this.bookmarks = bookmarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookmarkDTO> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<BookmarkDTO> bookmarks) {
        this.bookmarks = bookmarks;
    }
}