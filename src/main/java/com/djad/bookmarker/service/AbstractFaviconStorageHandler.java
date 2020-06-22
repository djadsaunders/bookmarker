package com.djad.bookmarker.service;

import java.util.UUID;

public abstract class AbstractFaviconStorageHandler implements FaviconStorageHandler {

    @Override
    public abstract byte[] readFile(String fileName);

    @Override
    public abstract String writeFile(byte[] faviconImage);
    
    protected String getRandomFilename() {
        return UUID.randomUUID().toString();
    }
}