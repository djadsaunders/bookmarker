package com.djad.bookmarker.dto;

public class BookmarkDTO {
    String category;
    String name;
    String url;
    long id;
    byte[] favicon;

    protected BookmarkDTO(String category, long id, String name, String url, byte[] favicon) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.url = url;
        this.favicon = favicon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getFavicon() {
        return favicon;
    }

    public void setFavicon(byte[] favicon) {
        this.favicon = favicon;
    }
}