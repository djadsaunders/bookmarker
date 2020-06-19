package com.djad.bookmarker.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="category")
@FilterDef(name="userFilter", parameters=@ParamDef(name="userId", type="string"))
@Filter(name="userFilter", condition=":userId=userid")
@NoArgsConstructor
@EqualsAndHashCode
public class Category {

    public static final String DEFAULT_NAME = "Unfiled";

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    @Getter private long id;

    @Column(name="userid")
    @EqualsAndHashCode.Include
    @Getter @Setter private String userId;

    @Column(name="name")
    @EqualsAndHashCode.Include
    @Getter @Setter private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @Getter @Setter private List<Bookmark> bookmarks;

    public Category(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}