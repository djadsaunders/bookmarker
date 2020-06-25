package com.djad.bookmarker;

import com.djad.bookmarker.service.DBStorageFaviconHandler;
import com.djad.bookmarker.service.FaviconStorageHandler;
import com.djad.bookmarker.service.LocalStorageFaviconHandler;
import com.djad.bookmarker.service.S3StorageFaviconHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BookmarkerApplicationContext {
    
    @Value("${favicon-storage-handler}")
    private String faviconStorageHandler;

    @Bean(name="faviconStorageHandler")
    public FaviconStorageHandler getFaviconStorageHandler() {
        FaviconStorageHandler handler = null;
        final String handlerType = this.faviconStorageHandler == null ? 
            new String("") : this.faviconStorageHandler;
        
        switch (handlerType) {
            case "LOCAL": {
                log.debug("Creating local favicon handler");
                handler = new LocalStorageFaviconHandler();
                break;
            }
            case "S3": {
                log.debug("Creating AWS S3 favicon handler");
                handler = new S3StorageFaviconHandler();
                break;
            }
            case "DB": {
                log.debug("Creating DB favicon handler");
                handler = new DBStorageFaviconHandler();
                break;
            }
            default: {
                log.debug("Creating default favicon handler");
                handler = new LocalStorageFaviconHandler();
                break;
            }
        }
        
        return handler;
    }
}