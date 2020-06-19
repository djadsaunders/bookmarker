package com.djad.bookmarker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

    public static final String FAVICON_FOLDER = "/opt/bookmarker/thumbs/";

    public static String writeFaviconFile(byte[] faviconImage) {
        FileOutputStream fos = null;

        String fileName = UUID.randomUUID().toString();
        File file = new File(FAVICON_FOLDER + fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            fos.write(faviconImage);
        }
        catch (IOException e) {
            log.error("Failed to write favicon file: " + e.getMessage());
        }
        finally {
            try {
                fos.close();
            }
            catch (Exception e) {
            }
        }
        return fileName;
    }

    public static byte[] readFaviconFile(String fileName) {
        File file = new File(FAVICON_FOLDER + fileName);
        byte[] result = new byte[(int)file.length()];

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            fis.read(result);
        }
        catch (IOException e) {
            log.error("Failed to write favicon file: " + e.getMessage());
        }
        finally {
            try {
                fis.close();
            }
            catch (Exception e) {
            }
        }

        return result;
    }

}