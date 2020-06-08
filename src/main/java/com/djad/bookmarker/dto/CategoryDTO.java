package com.djad.bookmarker.dto;

import java.util.List;

public class CategoryDTO {
    private String name;
    private List<BookmarkDTO> bookmarks;
    private long id;

    public CategoryDTO(long id, String name, List<BookmarkDTO> bookmarks) {
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}