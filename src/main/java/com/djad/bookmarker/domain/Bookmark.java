package com.djad.bookmarker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bookmark")
public class Bookmark {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @ManyToOne
    private Category category;

    @Column(name="url")
    private String url;

    @Column(name="pending")
    private boolean pending;

    public Bookmark() {
    }

    public Bookmark(Category category, String url, String name, boolean pending) {
        if (name == null) {
            this.name = url;
        }
        else {
            this.name = name;
        }
        this.category = category;
        this.url = url;
        this.pending = pending;
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
}