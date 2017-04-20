package com.hostandguest.entities;

import com.hostandguest.util.DesiredField;
import java.util.Date;

/**
 *
 * @author The
 */
public class Review {
    private int id;
    private String comment;
    @DesiredField(shownValue = "date comment", databaseProperName = "date_comment")
    private String video_url;
    private Date dateComment;
    @DesiredField(shownValue = "price quality", databaseProperName = "price_quality")
    private int price_quality;
    @DesiredField(shownValue = "position", databaseProperName = "lieu")
    private int lieu;
    @DesiredField(shownValue = "precision", databaseProperName = "precisionToDescription")
    private int precision;
    @DesiredField(shownValue = "communication", databaseProperName = "communication")
    private int communication;
    @DesiredField(shownValue = "cleanliness", databaseProperName = "cleanliness")
    private int cleanliness;
    @DesiredField(shownValue = "satisfaction level", databaseProperName = "satisfaction_level")
    private int satisfaction_level;
    private int mood;
    
    // Many To One
    private Booking booking;

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

    public int getPrice_quality() {
        return price_quality;
    }

    public void setPrice_quality(int price_quality) {
        this.price_quality = price_quality;
    }

    public int getLieu() {
        return lieu;
    }

    public void setLieu(int lieu) {
        this.lieu = lieu;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getCommunication() {
        return communication;
    }

    public void setCommunication(int communication) {
        this.communication = communication;
    }

    public int getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(int cleanliness) {
        this.cleanliness = cleanliness;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public int getSatisfaction_level() {
        return satisfaction_level;
    }

    public void setSatisfaction_level(int satisfaction_level) {
        this.satisfaction_level = satisfaction_level;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    @Override
    public String toString() {
        return "Review{" + "id=" + id + ", comment=" + comment + ", video_url=" + video_url + ", dateComment=" + dateComment + ", price_quality=" + price_quality + ", lieu=" + lieu + ", precision=" + precision + ", communication=" + communication + ", cleanliness=" + cleanliness + ", satisfaction_level=" + satisfaction_level + ", mood=" + mood + ", booking=" + booking + '}';
    }
}
