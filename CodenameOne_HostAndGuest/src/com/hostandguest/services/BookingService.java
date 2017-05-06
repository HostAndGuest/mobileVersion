/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.hostandguest.entities.Booking;
import com.hostandguest.entities.Property;
import com.hostandguest.entities.User;
import com.hostandguest.interfaces.IBooking;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.littlemonkey.connectivity.Connectivity;
import org.littlemonkey.connectivity.Connectivity.ConnectionState;

/**
 *
 * @author Toshiba
 */
public class BookingService implements IBooking {
    
    @Override
    public List<Booking> getUserBookings__ShowPropertyGiftList(int user_id, int property_id, boolean showGiftList) {
        List<Booking> bookings = new ArrayList<>();
        
        Map<String, Object> response = null;
        try {
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/getUserBookings__PropertyGiftList.php?"
                    + "property_id=" + property_id +"&showGiftList="+showGiftList
                    + "&user_id=" + user_id);
            
            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener((e) -> e.consume());
            
            response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));
            
            java.util.List<Map<String, Object>> responseContent = new ArrayList<>();

            try {
                responseContent = (java.util.List<Map<String, Object>>) response.get("booking");
            } catch (ClassCastException e) {
                // when only one record is found causes above exception
                try
                {
                    responseContent.add((Map<String, Object>) response.get("booking"));
                }
                catch (ClassCastException ex)
                {
                    Dialog.show("Error", "An Error Occured", "OK", null);
                }
            }
 
//            if (!responseContent.isEmpty())
            {
                for (Map<String, Object> record : responseContent) {
                    Property property = new Property();
                    Booking booking = new Booking();
                    User user = new User();
                    
                    booking.setId(Integer.valueOf(record.get("id").toString()));
                    booking.setTerm(Integer.valueOf(record.get("term").toString()));
                    booking.setNbr_rooms_reserved(Integer.valueOf(record.get("nb_reserved_rooms").toString()));
                    booking.setTotal_amount(Integer.valueOf(record.get("total_amount").toString()));
                    booking.setBookingDate(new SimpleDateFormat("yyyy-MM-d").parse(record.get("booking_date").toString()));
                    
                    property.setId(Integer.valueOf(record.get("property_id").toString()));
            
                    if (!showGiftList)
                    {
                        user.setLast_name(record.get("last_name").toString());
                        user.setFirst_name(record.get("first_name").toString());
                    }
                    
                    booking.setProperty(property);
                    booking.setGuest(user);
                    
                    bookings.add(booking);
                }
            }
        } catch (IOException | ParseException err) {
            Log.e(err);
        } catch (NullPointerException err) {
//            if (response.get("failed") != null)
            {
                if (!showGiftList)
                    Dialog.show("Information", "No Bookings Found", "OK", null);
                else
                    Dialog.show("Information", "No Gifts Found", "OK", null);
            }

            Log.e(err);
        }

        return bookings;
    }
    
    @Override
    public boolean deleteBooking(int booking_id) {
        try
        {
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/removeBookingById.php", true);
            
            connectionRequest.addArgument("booking_id", String.valueOf(booking_id));
            
            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener((e) -> e.consume());
            
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));
            
            if (response.get("status").toString().equals("success"))
                return true;
        }
        catch (Exception err)
        {
            Log.e(err);
        }
        
        return false;
    }   

    @Override
    public boolean acquireGift(int booking_id) {
        try
        {
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/acquireBookingGift.php", true);
            
            connectionRequest.addArgument("booking_id", String.valueOf(booking_id));
            connectionRequest.addArgument("user_id", String.valueOf(UserCourant.getCurrentuser().getId()));
            
            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener((e) -> e.consume());
            
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));
            
            if (response.get("status").toString().equals("success"))
                return true;
        }
        catch (Exception err)
        {
            Log.e(err);
        }
        
        return false;
    }

    @Override
    public boolean addBooking__GiftAnon(Booking booking, boolean isGift) {
        try
        {
            // date format
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ConnectionRequest connectionRequest;
            
            if (!isGift)
                connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/addBooking.php", true);
            else
                connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/giftBookingAnonymously.php", true);
                
            connectionRequest.addArgument("property_id", String.valueOf(booking.getProperty().getId()));
            connectionRequest.addArgument("guest_id", String.valueOf(UserCourant.getCurrentuser().getId()));
            connectionRequest.addArgument("term", String.valueOf(booking.getTerm()));
            connectionRequest.addArgument("booking_date", dateFormat.format(booking.getBookingDate()));
            connectionRequest.addArgument("nb_reserved_rooms", String.valueOf(booking.getNbr_rooms_reserved()));
            connectionRequest.addArgument("total_amount", String.valueOf(booking.getTotal_amount()));
            
            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener((e) -> e.consume());
            
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));
            
            if (response.get("status").toString().equals("success"))
                return true;
        }
        catch (Exception err)
        {
            Log.e(err);
        }
        
        return false;
    }

    @Override
    public int getFreeRooms(int property_id, Date bookingDate, int term) {
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://localhost/ScriptsHostAndGuest/getFreeRooms.php"
                    + "?bookingDate=" + dateFormat.format(bookingDate)
                    + "&property_id=" + property_id
                    + "&term=" + term);
            
            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener((e) -> e.consume());
            
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));
            
            if (response.get("FreeRooms") != null)
                return Integer.valueOf(response.get("FreeRooms").toString());
        }
        catch (IOException | NumberFormatException err)
        {
            Log.e(err);
        }
        
        return 0;
    }
}
