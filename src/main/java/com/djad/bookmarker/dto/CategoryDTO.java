package com.djad.bookmarker.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CategoryDTO {
    @Getter private long id;
    @Getter @Setter private String name;
    @Getter @Setter private List<BookmarkDTO> bookmarks;
}