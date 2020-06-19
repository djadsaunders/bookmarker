package com.djad.bookmarker.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

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

        byte[] result = null;

        InputStream in = null;
        ByteArrayOutputStream out = null;

        try {
            URL url = new URL(urlString);

            in = new BufferedInputStream(url.openStream());
            out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf))) {
               out.write(buf, 0, n);
            }
            result = out.toByteArray();            
        } 
        catch (Exception e) {
            logger.error(e.getMessage());
            // Leave icon empty on error
        }
        finally {
            try {
                out.close();
                in.close();
            }
            catch (Exception e) {
            }
        }

        return result;
    }
}