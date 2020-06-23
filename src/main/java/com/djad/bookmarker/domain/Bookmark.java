package com.djad.bookmarker.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name="bookmark")
@FilterDef(name="userFilter", parameters=@ParamDef(name="userId", type="string"))
@Filter(name="userFilter", condition=":userId=userid")
@Slf4j
@NoArgsConstructor
public class Bookmark {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private long id;

    @Column(name="userid")
    @Getter @Setter private String userId;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @Getter @Setter private Category category;

    @Column(name="url")
    @Getter @Setter private String url;

    @Column(name="name")
    @Getter @Setter private String name;

    @Column(name="pending")
    @Getter @Setter private boolean pending;

    @Column(name="faviconFile")
    @Getter @Setter private String faviconFile;

    /**
     * Create Bookmark with default name and pending flag.
     */
    public Bookmark(String userId, Category category, String url, String faviconFile) {
        this(userId, category, url, null, true, faviconFile);
    }

    /**
     * Create Bookmark with all values.
     */
    public Bookmark(String userId, Category category, String url, String name, boolean pending, 
        String faviconFile) {

        log.debug("Create Bookmark");

        this.userId = userId;
        this.category = category;
        this.url = url;
        this.pending = pending;
        this.faviconFile = faviconFile;

        // If name isn't specified, use URL
        if (name == null) {
            this.name = url;
        }
        else {
            this.name = name;
        }
    }
}