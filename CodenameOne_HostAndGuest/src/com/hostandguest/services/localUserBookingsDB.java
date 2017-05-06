/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.services;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.Log;
import com.hostandguest.entities.Booking;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Toshiba
 */
public class localUserBookingsDB {
    private Database localDB;
    
    public localUserBookingsDB() {
        boolean exists = Database.exists("userReservations");
        try
        {
            localDB = Database.openOrCreate("userReservations");
            
            if (!exists)
            {
                // date type is either Text, Real or Numeric 
                // Our date format is "YYYY-MM-DD HH:MM:SS.SSS" of type TEXT.
                localDB.execute("create table booking(id integer PRIMARY KEY, "
                        + "property_id integer, guest_id integer, term integer, "
                        + "booking_date text, nb_reserved_rooms integer, total_amount real);");
            }
        }
        catch (IOException err) {
            Log.e(err);
        }
    }
    
    public List<Booking> localReservationsList(List<Booking> bookings, int property_id)
    {
        try
        {
            Cursor c = localDB.executeQuery("select * from booking where guest_id = "
                    + UserCourant.getCurrentuser().getId() + " and property_id = " 
                    + property_id);
            
            while (c.next())
            {
                Booking b = new Booking();
                Row r = c.getRow();
                
                b.setId(r.getInteger(0));
                b.setTerm(r.getInteger(3));
                b.setBookingDate(new SimpleDateFormat("yyyy-MM-d").parse(r.getString(4)));
                b.setNbr_rooms_reserved(r.getInteger(5));
                b.setTotal_amount(r.getInteger(6));
                
                bookings.add(b);
            }
        }
        catch (IOException | ParseException err) {
            Log.e(err);
        }
        
        return bookings;
    }
    
    public boolean insertBooking(Booking b)
    {
        // date format
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
        try
        {
            localDB.execute("insert into booking (property_id, guest_id, term, booking_date, nb_reserved_rooms, total_amount) " 
                    + "values(" + b.getProperty().getId() + ", " + UserCourant.getCurrentuser().getId() + ", " 
                    + b.getTerm() + ", '"+ dateFormat.format(b.getBookingDate()) + "', " 
                    + b.getNbr_rooms_reserved()+", " + b.getTotal_amount() + ");");
        }
        catch (IOException ex)
        {
            Log.e(ex);
            return false;
        }
        
        return true;
    }
    
    /**
     * @param booking_id
     * @param purgeClause used to delete the entire local database content
     * @return 
     */
    public boolean deleteBooking(int booking_id, String purgeClause)
    {
        try
        {
            localDB.execute("delete from booking where id = " + booking_id + " " + purgeClause);
//purgeclause to remove a table or index from your recycle bin and release all of the space associated with the object
        }
        catch (IOException ex)
        {
            Log.e(ex);
            return false;
        }
        
        return true;
    }
}
