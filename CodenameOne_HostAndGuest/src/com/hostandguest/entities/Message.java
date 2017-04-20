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
public class Message {
    public static int currentUserConversation;
    public static int currentUserMail;
    private int id;
    private int thread_id;
    private int sender_id;
    private String body;
    private Date created_at;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", thread_id=" + thread_id + ", sender_id=" + sender_id + ", body=" + body + ", created_at=" + created_at + '}';
    }
}
