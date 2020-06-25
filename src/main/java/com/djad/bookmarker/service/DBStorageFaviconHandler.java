package com.djad.bookmarker.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.djad.bookmarker.domain.Favicon;
import com.djad.bookmarker.repository.FaviconRepository;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBStorageFaviconHandler extends AbstractFaviconStorageHandler {

    @Autowired
    private FaviconRepository faviconRepository;

    @Override
    @Transactional
    public byte[] readFile(String fileName) {

        byte[] result = null;

        Optional<Favicon> favicon = faviconRepository.findByName(fileName);
        if (!favicon.isPresent()) {
            log.error("Favicon " + fileName + " not found");
            return result;
        }

        return favicon.get().getImage();
    }

    @Override
    @Transactional
    public String writeFile(byte[] faviconImage) {

        String fileName = this.getRandomFilename();
 
        try {
            Favicon favicon = new Favicon(fileName, faviconImage);
            faviconRepository.save(favicon);
        }
        catch (Exception e) {
            fileName = null;
            log.error("Failed to save favicon: " + e.getMessage());
        }

        return fileName;
    }
    
    
}