package com.djad.bookmarker;

import com.djad.bookmarker.service.BootstrapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BookmarkerApplication {

	@Autowired
	public BootstrapService service;

	public static void main(String[] args) {
		SpringApplication.run(BookmarkerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady(ApplicationReadyEvent event) {
		service.seedData();
	}
}
