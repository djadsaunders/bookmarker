package com.djad.bookmarker.service;

public interface FaviconStorageHandler {
    String writeFile(byte[] faviconImage);
    byte[] readFile(String fileName);
}