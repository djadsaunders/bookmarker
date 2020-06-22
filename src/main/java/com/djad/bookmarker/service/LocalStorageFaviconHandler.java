package com.djad.bookmarker.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LocalStorageFaviconHandler extends AbstractFaviconStorageHandler {

    private static final String FAVICON_FOLDER = "/opt/bookmarker/thumbs/";

    @Override
    public String writeFile(byte[] faviconImage) {
        log.debug("Called writeFile()");
        
        String fileName = getRandomFilename();
        File file = new File(FAVICON_FOLDER + fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            @Cleanup FileOutputStream fos = new FileOutputStream(file);
            fos.write(faviconImage);
        }
        catch (IOException e) {
            log.error("Failed to write favicon file: " + e.getMessage());
        }

        return fileName;
    }

    @Override
    public byte[] readFile(String fileName) {
        log.debug("Called readFile()");

        File file = new File(FAVICON_FOLDER + fileName);
        byte[] result = new byte[(int)file.length()];

        try {
            @Cleanup FileInputStream fis = new FileInputStream(file);
            fis.read(result);
        }
        catch (IOException e) {
            log.error("Failed to write favicon file: " + e.getMessage());
        }

        return result;
    }

}