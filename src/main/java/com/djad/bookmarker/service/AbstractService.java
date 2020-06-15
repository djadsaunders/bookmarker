package com.djad.bookmarker.service;

import javax.persistence.EntityManager;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {

    @Autowired
    private EntityManager entityManager;

    public String getCurrentUserName() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    protected void setUserFilter() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("userFilter");
        filter.setParameter("userId", this.getCurrentUserName());
    }
}