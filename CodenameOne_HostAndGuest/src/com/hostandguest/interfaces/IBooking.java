/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.interfaces;

import com.hostandguest.entities.Booking;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public interface IBooking {
    public List<Booking> getUserBookings__ShowPropertyGiftList(int user_id, int pid, boolean showGiftList);
    public boolean deleteBooking(int booking_id);
    public boolean acquireGift(int booking_id);
    public boolean addBooking__GiftAnon(Booking booking, boolean isGift);
    public int getFreeRooms(int property_id, Date bookingDate, int term);
}
