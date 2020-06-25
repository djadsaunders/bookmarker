package com.djad.bookmarker.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import lombok.Cleanup;

@Component
public class FileUtils {
    
    public void writeFile(String filePath, byte[] contents) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            file.createNewFile();
        }

        @Cleanup FileOutputStream fos = new FileOutputStream(file);
        fos.write(contents);
    }

    public byte[] readFileAsByteArray(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] result = new byte[(int)file.length()];

        @Cleanup FileInputStream fis = new FileInputStream(file);
        fis.read(result);

        return result;
    }
}