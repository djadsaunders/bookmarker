package com.djad.bookmarker.domain;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="favicon")
@NoArgsConstructor
public class Favicon {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private long id;

    @Column(name="name")
    @Getter @Setter private String name;

    @Column(name="image")
    @Lob
    @Getter @Setter byte[] image;

    public Favicon(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }
}