package com.djad.bookmarker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class BookmarkDTO {
    @Getter @Setter private String category;
    @Getter private long id;
    @Getter @Setter private String name;
    @Getter @Setter private String url;
    @Getter @Setter private byte[] faviconContents;
    @Getter @Setter private String faviconFile;
}