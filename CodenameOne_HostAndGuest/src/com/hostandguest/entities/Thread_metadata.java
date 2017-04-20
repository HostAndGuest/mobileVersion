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
public class Thread_metadata {
    private int id;
    private int thread_id;
    private int participant_id;
    private boolean is_deleted;
    private Date last_participant_message_date;
    private Date last_message_date;

    public Thread_metadata() {
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

    public int getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(int participant_id) {
        this.participant_id = participant_id;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Date getLast_participant_message_date() {
        return last_participant_message_date;
    }

    public void setLast_participant_message_date(Date last_participant_message_date) {
        this.last_participant_message_date = last_participant_message_date;
    }

    public Date getLast_message_date() {
        return last_message_date;
    }

    public void setLast_message_date(Date last_message_date) {
        this.last_message_date = last_message_date;
    }

    @Override
    public String toString() {
        return "Thread_metadata{" + "id=" + id + ", thread_id=" + thread_id + ", participant_id=" + participant_id + ", is_deleted=" + is_deleted + ", last_participant_message_date=" + last_participant_message_date + ", last_message_date=" + last_message_date + '}';
    }
}
