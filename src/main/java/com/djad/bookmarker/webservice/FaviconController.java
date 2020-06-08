package com.djad.bookmarker.webservice;

import java.io.FileOutputStream;

import com.djad.bookmarker.service.FaviconService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/favicon")
public class FaviconController {

    Logger logger = LoggerFactory.getLogger(FaviconController.class);

    private FaviconService service;

    @Autowired
    public FaviconController(FaviconService service) {
        this.service = service;
    }

    @GetMapping
    public void getFavicon(@RequestParam("url") String bookmarkUrl) {
        logger.debug("Get favicon");

        try {
            byte[] imageBytes = service.getFaviconAsByteArray(bookmarkUrl);

            FileOutputStream fos = new FileOutputStream("/home/dan/dev/testimage");
            fos.write(imageBytes);
            fos.close();
        } 
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
