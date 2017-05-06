/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.hostandguest.entities.Booking;
import com.hostandguest.services.BookingService;
import com.hostandguest.services.UserCourant;
import com.hostandguest.services.localUserBookingsDB;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.littlemonkey.connectivity.Connectivity;

/**
 * @author
 */
public class UserBookingList__PropertyGiftList {

    protected Form formList;
    protected Resources theme;

    public UserBookingList__PropertyGiftList(int property_id, boolean showGiftList, Form propertyDetailForm) {
        theme = UIManager.initFirstTheme("/theme");
        String title = "My Bookings";
        boolean isConnected = Connectivity.isConnected();

        if (showGiftList) {
            title = "Property Gift List";
        }

        formList = new Form(title, BoxLayout.y());
        List<Booking> bookings;

        if (isConnected) {
            bookings = new BookingService().getUserBookings__ShowPropertyGiftList(
                    UserCourant.getCurrentuser().getId(), property_id, showGiftList);

            // we refill the entire local db with the new data
            // we purge it here
            if (!showGiftList) {
                new localUserBookingsDB().deleteBooking(0, " or 1 = 1");
            }
        } else {
            title = "No Connection, Loading Local Data";

            if (showGiftList) {
                title = "No Connection";
            }

            ToastBar.Status status = ToastBar.getInstance().createStatus();
            status.setMessage(title);
            status.setExpires(3000);  // only show the status for 3 seconds, then have it automatically clear
            status.show();

            if (!showGiftList) {
                bookings = new localUserBookingsDB().localReservationsList(new ArrayList<>(), property_id);
            } else {
                bookings = new ArrayList<>();
            }
        }

        for (Booking booking : bookings) {
            Container ctnModelMain = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container ctnProfileInfo = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container ctnBookingInfo = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container ctnBtns = new Container(new BoxLayout(BoxLayout.X_AXIS));

            ImageViewer profilePic = new ImageViewer(theme.getImage("userProfilePicture.jpg"));

            if (showGiftList) {
                profilePic = new ImageViewer(theme.getImage("anonymous_user.png"));
            }

            profilePic.setPreferredSize(new Dimension(100, 100));
            ctnProfileInfo.add(profilePic);

            if (!showGiftList && isConnected) // this is equivalent to new Label(string)
            {
                ctnProfileInfo.add(booking.getGuest().getLast_name() + " " + booking.getGuest().getFirst_name());
            }

            // date format
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            ctnBookingInfo.add("Booking Date On : " + dateFormat.format(booking.getBookingDate()));
            ctnBookingInfo.add("Number of Reserved Rooms : " + booking.getNbr_rooms_reserved());
            ctnBookingInfo.add("Total Amount : " + booking.getTotal_amount());
            ctnBookingInfo.add("Booking Term : " + booking.getTerm());

            if (showGiftList) {
                Button btnAcquireGift = new Button("Acquire");

                btnAcquireGift.addActionListener((e) -> {
                    if (new BookingService().acquireGift(booking.getId())) {
                        ctnModelMain.remove();
                        Dialog.show("Notice", "Gift Acquired", "OK", null);
                        new UserBookingList__PropertyGiftList(property_id, false, propertyDetailForm).getFormList().show();
                    } else {
                        Dialog.show("Notice", "An Error Occured While Acquiring The Gift", "OK", null);
                    }
                });

                ctnBtns.add(btnAcquireGift);
            } else {
                if (isConnected) {
                    Button btnCancel = new Button("Cancel");

                    btnCancel.addActionListener((e) -> {
                        if (new BookingService().deleteBooking(booking.getId())) {
                            Dialog.show("Notice", "Booking Removed", "OK", null);
                            ctnModelMain.remove();
                        } else {
                            Dialog.show("Notice", "An Error Occured While Removing Your Reservation", "OK", null);
                        }
                    });

                    ctnBtns.add(btnCancel);
                }
            }

            ctnBookingInfo.add(ctnBtns);
            ctnModelMain.add(ctnProfileInfo);
            ctnModelMain.add(ctnBookingInfo);

            ctnModelMain.setScrollableX(true);

            // we refill the entire local db with the new data
            // we insert new rows here
            if (!showGiftList && isConnected) {
                new localUserBookingsDB().insertBooking(booking);
            }

            formList.add(ctnModelMain);
        }

        // setting back command if gift list
        if (showGiftList) {
            Style s = UIManager.getInstance().getComponentStyle("Title");
            FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
            Command goBackCmd = new Command("", icon) {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    propertyDetailForm.showBack();
                }
            };

            formList.getToolbar().addCommandToRightBar(goBackCmd);
        } else {
            // setting menu
            new CommonMenu(theme, formList);
        }
    }

    public Form getFormList() {
        return formList;
    }
}
