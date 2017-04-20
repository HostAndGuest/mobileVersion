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
public class Booking {
    private int id;
    private int term;
    private boolean reviewed;
    private Date bookingDate;
    private int nbr_rooms_reserved;
    private double total_amount;
    private boolean isGift;
  
    // Many To One 
    private Property property;
    
    // Many To One
    private User guest;


    public Booking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }
  public int getNbr_rooms_reserved() {
        return nbr_rooms_reserved;
    }

    public void setNbr_rooms_reserved(int nbr_rooms_reserved) {
        this.nbr_rooms_reserved = nbr_rooms_reserved;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public boolean isIsGift() {
        return isGift;
    }

    public void setIsGift(boolean isGift) {
        this.isGift = isGift;
    }

    @Override
    public String toString() {
        return "Booking{" + "id=" + id + ", term=" + term + ", reviewed=" + reviewed + ", bookingDate=" + bookingDate + ", nbr_rooms_reserved=" + nbr_rooms_reserved + ", total_amount=" + total_amount + ", isGift=" + isGift + ", property=" + property + ", guest=" + guest + '}';
    }
}
