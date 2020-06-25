package com.djad.bookmarker.service;

import com.djad.bookmarker.util.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LocalStorageFaviconHandler extends AbstractFaviconStorageHandler {

    private static final String FAVICON_FOLDER = "/opt/bookmarker/thumbs/";

    @Autowired
    private FileUtils fileUtils;

    @Override
    public String writeFile(byte[] faviconImage) {
        log.debug("Called writeFile()");
        
        String fileName = this.getRandomFilename();

        try {
            String filePath = FAVICON_FOLDER + fileName;
            fileUtils.writeFile(filePath, faviconImage);
        }
        catch (Exception e) {
            fileName = null;
            log.error("Failed to write favicon: " + e.getMessage());
        }

        return fileName;
    }

    @Override
    public byte[] readFile(String fileName) {
        log.debug("Called readFile()");

        byte[] result = null;

        try {
            result = fileUtils.readFileAsByteArray(FAVICON_FOLDER + fileName);
        }
        catch (Exception e) {
            log.error("Failed to read favicon: " + e.getMessage());
        }

        return result;
    }

}