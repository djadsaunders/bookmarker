package com.djad.bookmarker.domain;

import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;

import com.djad.bookmarker.ApplicationException;

@Entity
@Table(name="bookmark")
public class Bookmark {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Category category;

    @Column(name="url")
    private String url;

    @Column(name="pending")
    private boolean pending;

    @Column(name="favicon")
    private Blob favicon;

    public Bookmark() {
    }

    public Bookmark(Category category, String url, String name, boolean pending, byte[] faviconBytes) {

        this.category = category;
        this.url = url;
        this.pending = pending;

        // If name isn't specified, use URL
        if (name == null) {
            this.name = url;
        }
        else {
            this.name = name;
        }
        
        // Create Blob for image from byte[]
        // If array is empty, don't try
        try {
            if (faviconBytes != null && faviconBytes.length > 0) this.favicon = new SerialBlob(faviconBytes);
        }
        catch (Exception e) {
            throw new ApplicationException("Failed to create Blob for favicon", e);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getCategory().getName()).append(":").
            append(this.getName()).append(":").
            append(this.getUrl());
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public Blob getFavicon() {
        return favicon;
    }

    public void setFavicon(Blob favicon) {
        this.favicon = favicon;
    }
}