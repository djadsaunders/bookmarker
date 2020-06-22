package com.djad.bookmarker.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import org.springframework.stereotype.Component;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class S3StorageFaviconHandler extends AbstractFaviconStorageHandler {

    private static final String BUCKET_NAME = "djs-bookmarker-thumbs";

    @Override
    public byte[] readFile(String keyName) {
        log.debug("Called readFile()");

        final AmazonS3 s3 = getS3();
        
        byte[] result = new byte[0];

        try {
            S3Object s3Obj = s3.getObject(BUCKET_NAME, keyName);
            @Cleanup S3ObjectInputStream s3is = s3Obj.getObjectContent();
            result = IOUtils.toByteArray(s3is);
        }
        catch (Exception e) {
            log.error("Failed to read favicon file: " + e.getMessage());
        }

        return result;
    }

    @Override
    public String writeFile(byte[] faviconImage) {

        log.debug("Calling writeFile()");

        String fileName = getRandomFilename();

        final AmazonS3 s3 = getS3();

        // Process writes a file to upload then deletes in when done
        // Maybe there is a better way to do this...

        File file = new File(fileName);

        try {
            @Cleanup FileOutputStream fos = new FileOutputStream(file);
            fos.write(faviconImage);
            s3.putObject(BUCKET_NAME, fileName, file);
            log.debug("Successfully wrote file to S3");
        } 
        catch (Exception e) {
            log.error("Failed to write file to S3: " + e.getMessage());
            return null;
        }
        finally {
            file.delete();
        }

        return fileName;
    }

    private AmazonS3 getS3() {
        return AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
    }
    
}