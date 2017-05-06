/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.File;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.hostandguest.entities.Booking;
import com.hostandguest.entities.Property;
import com.hostandguest.entities.Review;
import com.hostandguest.entities.User;
import com.hostandguest.interfaces.IReview;
import com.hostandguest.util.AnalyticDictionary;
import com.hostandguest.util.Satisfaction;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 
 */
public class ReviewService implements IReview {
    
    @Override
    public List<Review> getPropertyReviews(int property_id) {
        List<Review> reviews = new ArrayList<>();
        Map<String, Object> response = null;
        try {
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/getPropertyReviews.php?property_id=" + property_id);

            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener(e -> e.consume());

            response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));

            java.util.List<Map<String, Object>> responseContent = new ArrayList<>();

            try {
                responseContent = (java.util.List<Map<String, Object>>) response.get("review");
            } catch (ClassCastException e) {
                // when only one record is found causes above exception
                responseContent.add((Map<String, Object>) response.get("review"));
            }

//            if (!responseContent.isEmpty())
            {
                for (Map<String, Object> record : responseContent) {
                    Review review = new Review();
                    Property property = new Property();
                    Booking booking = new Booking();
                    User user = new User();

                    review.setId(Integer.valueOf(record.get("rid").toString()));
                    review.setComment(record.get("givenComment").toString());
                    review.setDateComment(new SimpleDateFormat("yyyy-MM-d").parse(record.get("date_comment").toString()));
                    review.setVideo_url(record.get("video_url").toString());

                    review.setPrice_quality(Integer.valueOf(record.get("price_quality").toString()));
                    review.setLieu(Integer.valueOf(record.get("lieu").toString()));
                    review.setPrecision(Integer.valueOf(record.get("precisionToDescription").toString()));
                    review.setCommunication(Integer.valueOf(record.get("communication").toString()));
                    review.setCleanliness(Integer.valueOf(record.get("cleanliness").toString()));

                    review.setSatisfaction_level(Integer.valueOf(record.get("satisfaction_level").toString()));
                    review.setMood(Integer.valueOf(record.get("mood").toString()));

                    booking.setId(Integer.valueOf(record.get("bid").toString()));
                    booking.setTerm(Integer.valueOf(record.get("term").toString()));
                    booking.setReviewed(Boolean.valueOf(record.get("reviewed").toString()));
                    booking.setBookingDate(new SimpleDateFormat("yyyy-MM-d").parse(record.get("booking_date").toString()));

                    property.setId(property_id);

                    user.setId(Integer.valueOf(record.get("uid").toString()));
                    user.setLast_name(record.get("last_name").toString());
                    user.setFirst_name(record.get("first_name").toString());

                    booking.setProperty(property);
                    booking.setGuest(user);
                    review.setBooking(booking);

                    reviews.add(review);
                }
            }
        } catch (IOException | ParseException err) {
            Log.e(err);
        } catch (NullPointerException err) {
//            if (response.get("failed") != null)
            {
                Dialog.show("Information", "No Reviews Found", "OK", null);
            }

            Log.e(err);
        }

        return reviews;
    }

    @Override
    public boolean deleteReview(int review_id, int booking_id) {
        try {
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/deleteReview.php", true);

            connectionRequest.addArgument("review_id", String.valueOf(review_id));
            connectionRequest.addArgument("booking_id", String.valueOf(booking_id));

            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener(e -> e.consume());

            if (new String(connectionRequest.getResponseData()).startsWith("{\"success")) {
                return true;
            }
        }
        catch (Exception err)
        {
            Log.e(err);
        }

        return false;
    }

    @Override
    public List<Booking> getOpenBookings(int property_id) {
        List<Booking> bookings = new ArrayList<>();
        
        try
        {
            ConnectionRequest connectionRequest = new ConnectionRequest(
                    "http://localhost/ScriptsHostAndGuest/getReviewOpenReservations.php?property_id=" + property_id
                    + "&user_id=" + UserCourant.getCurrentuser().getId());

            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener(e -> e.consume());

            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));

            java.util.List<Map<String, Object>> responseContent = new ArrayList<>();

            try
            {
                responseContent = (java.util.List<Map<String, Object>>) response.get("booking");
            }
            catch (ClassCastException e)
            {
                // when only one record is found causes above exception
                responseContent.add((Map<String, Object>) response.get("booking"));
            }

            for (Map<String, Object> record : responseContent)
            {
                Booking booking = new Booking();
                User user = new User();
                
                booking.setId(Integer.valueOf(record.get("id").toString()));
                booking.setTerm(Integer.valueOf(record.get("term").toString()));
                booking.setReviewed(Boolean.valueOf(record.get("reviewed").toString()));
                booking.setBookingDate(new SimpleDateFormat("yyyy-MM-d").parse(record.get("booking_date").toString()));
                
                user.setId(Integer.valueOf(record.get("guest_id").toString()));
                
                booking.setGuest(user);
                
                bookings.add(booking);
            }
        }
        catch (IOException | ParseException | NullPointerException err) {
            Log.e(err);
        }
        
        return bookings;
    }

    @Override
    public boolean add(Review review, boolean videoUploaded) {
        try {
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/addReview.php", true);
            
            if (videoUploaded)
            {
                connectionRequest.addArgument("rid", String.valueOf(getFutureID()));
                connectionRequest.addArgument("videoUrl", review.getVideo_url());
            }
            else
            {
                connectionRequest.addArgument("rid", "0");
                connectionRequest.addArgument("videoUrl", "notNeededForThisQuery");
            }
            
            connectionRequest.addArgument("booking_id", String.valueOf(review.getBooking().getId()));
            connectionRequest.addArgument("comment", String.valueOf(review.getComment()));
            connectionRequest.addArgument("priceQ", String.valueOf(review.getPrice_quality()));
            connectionRequest.addArgument("position", String.valueOf(review.getLieu()));
            connectionRequest.addArgument("precision", String.valueOf(review.getPrecision()));
            connectionRequest.addArgument("com", String.valueOf(review.getCommunication()));
            connectionRequest.addArgument("clean", String.valueOf(review.getCleanliness()));
            
            Satisfaction commentSatisfaction = determineSatisfactionLevel(review.getComment());
            
            connectionRequest.addArgument("sLevel", String.valueOf(commentSatisfaction.getsLevel()));
            connectionRequest.addArgument("mood", String.valueOf(commentSatisfaction.getMood()));
            
            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener(e -> e.consume());
            
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));
            
            if (response.get("status").toString().equals("success"))
                return true;
            else
                System.out.println(response);

        }
        catch (IOException | NullPointerException err) {
            Log.e(err);
        }

        return false;
    }

    @Override
    public Satisfaction determineSatisfactionLevel(String comment) {
        int mood = 0, sLevel = 0, satisfaction = 0;
        int happyLevel = 0, angerLevel = 0, sadnessLevel = 0;
        // if the number of words between the negation and the next word is more than a certain amount 
        // or a dot is found
        // this will consider the negation is about the last emotion word
        int wordGap = 0;
        String lastMatchWord = "";
        boolean negationFound = false;
        // evaluated is used due to the negation
        // valuing each word found that repsents an emotion
        // and later in the negation process
        // if the negation concerns the last word it will reduce the value of the appropriate emotion 
        boolean evaluated = false;
        
        List<String> commentWords = Arrays.asList(org.apache.commons.lang3.StringUtils.split(comment));
        for (String word : commentWords)
        {
            if (AnalyticDictionary.getNEGATION().contains(word))
            {
                negationFound = true;
                wordGap = 0;
                continue;
            }
            
            // marks end of sentence
            if (word.charAt(word.length() - 1) == '.' || word.charAt(0) == '.')
            {
                lastMatchWord = "";
                negationFound = false;
                wordGap = 0;
                
                // if dot is at the beggining of the word we still check the word
                if (word.charAt(0) == '.')
                    word = word.substring(1, word.length());
                else
                    continue;
            }
                
            if (negationFound)
            {   
                // if more than 5 words passed and still no emotion found 
                // this will mark last found word as the one meant for the negation
                // second part of the condition is if the word is the last word in the sentence
                if (wordGap >= 5 || commentWords.indexOf(word) == commentWords.size() - 1)
                {
                    if (AnalyticDictionary.getJOY().contains(lastMatchWord))
                    {
                        happyLevel--;
                        angerLevel++;
                    }
                    else if (AnalyticDictionary.getANGER().contains(lastMatchWord))
                    {
                        angerLevel--;
                        happyLevel++;
                    }
                    else if (AnalyticDictionary.getSADNESS().contains(lastMatchWord))
                    {
                        sadnessLevel--;
                        happyLevel++;
                    }
                    else if (AnalyticDictionary.getJOY().contains(word))
                    {
                        angerLevel++;
                    }
                    else if (AnalyticDictionary.getANGER().contains(word) || AnalyticDictionary.getSADNESS().contains(word))
                    {
                        happyLevel++;
                    }
                    
                    lastMatchWord = "";
                    wordGap = 0;
                    negationFound = false;
                    continue;
                }
                
                // the gap is else below maximum and therefore 
                // check if the word represents an emotion 
                // knowing we're in the negation 
                
                if (AnalyticDictionary.getJOY().contains(word))
                {
                    angerLevel++;
                    
                    negationFound = false;
                    lastMatchWord = "";
                    wordGap = 0;
                }
                else if (AnalyticDictionary.getANGER().contains(word) || AnalyticDictionary.getSADNESS().contains(word))
                {
                    happyLevel++;
                    
                    negationFound = false;
                    lastMatchWord = "";
                    wordGap = 0;
                }
                
                continue;
            }
            
            // evaluating every word that matches an emotion
            // and later on if the gap exceeds the maximum 
            // reduce based on what was leveled up
            if (AnalyticDictionary.getJOY().contains(word))
            {
                lastMatchWord = word;
                happyLevel++;
            }
            else if (AnalyticDictionary.getANGER().contains(word))
            {
                lastMatchWord = word;
                angerLevel++;
            }
            else if (AnalyticDictionary.getSADNESS().contains(word))
            {
                lastMatchWord = word;
                sadnessLevel++;
            }
            
            wordGap++;
        }
        
        // based on the results determine mood and satisfaction level
        // in equality cases it will be undertermined
        
        // undertermined means nothing was evaluated 
        // might be a language barrier or not enough words
        if (happyLevel == 0 & angerLevel == 0 & sadnessLevel == 0)
            return new Satisfaction(0, 0);
        
        if (happyLevel > angerLevel + sadnessLevel)
            mood = 1;
        else if (sadnessLevel > happyLevel && sadnessLevel > angerLevel)
            mood = 2;
        else if (angerLevel > happyLevel && angerLevel > sadnessLevel)
            mood = 3;
        
        // satisfaction represents how high was he satisfied based on the total emotion keywords expressed
        sLevel = (int) ((double) happyLevel / (happyLevel + sadnessLevel + angerLevel) * 9);
        
        return new Satisfaction(mood, sLevel);
    }

    @Override
    public int getFutureID() {
        try {
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/getReviewNewID.php");

            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener(e -> e.consume());

            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));

            return Integer.valueOf(response.get("futureID").toString());
        }
        catch (Exception err)
        {
            Log.e(err);
        }

        return 1;
    }

    @Override
    public void uploadVideo(String filePath) {
        try
        {
            // call to different webservice because fileName in php was taken as the fileName 
            // but the method (here) misees from the oneas in apache library
            MultipartRequest request = new MultipartRequest();
                request.setUrl("http://localhost/ScriptsHostAndGuest/uploadMobile.php?fileName=" + getFutureID());
                request.addData("userfile", filePath, "video/mp4");
                NetworkManager.getInstance().addToQueue(request);
        }
        catch (IOException err)
        {
            Log.e(err);
        }
    }
}
