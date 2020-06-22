package com.djad.bookmarker.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FaviconService {

    public static final String URL_PREFIX = "https://www.google.com/s2/favicons";

    private FaviconStorageHandler faviconStorageHandler;

    @Autowired
    public FaviconService(FaviconStorageHandler faviconStorageHandler) {
        this.faviconStorageHandler = faviconStorageHandler;
    }

    public String getAndStoreFavicon(String url) {
        return faviconStorageHandler.writeFile(this.getFaviconAsByteArray(url));
    }

    public byte[] readStoredFavicon(String faviconName) {
        return faviconStorageHandler.readFile(faviconName);
    }

    public byte[] getFaviconAsByteArray(String urlString) {

        log.debug("Get favicon");

        urlString = URL_PREFIX + "?domain=" + urlString;

        byte[] result = null;

        try {
            URL url = new URL(urlString);
            @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();
            @Cleanup InputStream in = new BufferedInputStream(url.openStream());
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf))) {
               out.write(buf, 0, n);
            }
            result = out.toByteArray();            
        } 
        catch (Exception e) {
            // Leave icon empty on error
            log.error(e.getMessage());
        }

        return result;
    }
}