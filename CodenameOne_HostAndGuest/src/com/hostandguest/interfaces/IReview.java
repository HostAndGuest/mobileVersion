/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.interfaces;

import com.hostandguest.entities.Booking;
import com.hostandguest.entities.Review;
import com.hostandguest.util.Satisfaction;
import java.util.List;

/**
 * 
 */
public interface IReview {
    public List<Review> getPropertyReviews(int property_id);
    public boolean deleteReview(int review_id, int booking_id);
    public List<Booking> getOpenBookings(int property_id);
    public boolean add(Review r, boolean videoUploaded);
    public Satisfaction determineSatisfactionLevel(String comment);
    public int getFutureID();
    public void uploadVideo(String filePath);
}
