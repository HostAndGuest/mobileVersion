/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.entities;

import java.util.Date;

/**
 *
 * @author The
 */
public class Thread {
    private int id;
    private int created_by_id;
    private String subject;
    private Date created_at;
    private boolean is_spam;

    public Thread() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreated_by_id() {
        return created_by_id;
    }

    public void setCreated_by_id(int created_by_id) {
        this.created_by_id = created_by_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public boolean isIs_spam() {
        return is_spam;
    }

    public void setIs_spam(boolean is_spam) {
        this.is_spam = is_spam;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Thread{" + "id=" + id + ", created_by_id=" + created_by_id + ", subject=" + subject + ", created_at=" + created_at + ", is_spam=" + is_spam + '}';
    }
}
