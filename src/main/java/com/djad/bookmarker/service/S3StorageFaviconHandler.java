package com.djad.bookmarker.service;

import java.io.IOException;

import com.djad.bookmarker.util.AmazonS3Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class S3StorageFaviconHandler extends AbstractFaviconStorageHandler {

    private static final String BUCKET_NAME = "djs-bookmarker-thumbs";

    @Autowired
    private AmazonS3Utils s3Utils;

    @Override
    public byte[] readFile(String keyName) {
        log.debug("Called readFile()");

        byte[] result = null;

        try {
            result = s3Utils.getObjectAsByteArray(BUCKET_NAME, keyName);
        }
        catch (IOException e) {
            log.error("Failed to get S3 object " + e.getMessage());
        }

        return result;
    }

    @Override
    public String writeFile(byte[] faviconImage) {

        log.debug("Calling writeFile()");

        String fileName = getRandomFilename();

        try {
            s3Utils.putObject(BUCKET_NAME, fileName, faviconImage);
        }
        catch (Exception e) {
            fileName = null;
        }

        return fileName;
    }
}