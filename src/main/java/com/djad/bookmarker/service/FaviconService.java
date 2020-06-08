package com.djad.bookmarker.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.djad.bookmarker.ApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FaviconService {

    public static final String URL_PREFIX = "https://www.google.com/s2/favicons";

    Logger logger = LoggerFactory.getLogger(FaviconService.class);

    public byte[] getFaviconAsByteArray(String bookmarkUrl) {

        logger.debug("Get favicon");

        String urlString = URL_PREFIX + "?domain=" + bookmarkUrl;

        byte[] result = {};

        try {
            URL url = new URL(urlString);

            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf))) {
               out.write(buf, 0, n);
            }
            out.close();
            in.close();
            result = out.toByteArray();            
        } 
        catch (IOException e) {
            logger.error(e.getMessage());
            throw new ApplicationException("Error reading image", e);
        }

        return result;
    }
}