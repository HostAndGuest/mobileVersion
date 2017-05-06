/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.NumericSpinner;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.hostandguest.entities.Booking;
import com.hostandguest.entities.Property;
import com.hostandguest.services.BookingService;
import java.util.Date;

/**
 * @author Toshiba
 */
public class AddBooking {

    protected Form formAdd;
    protected Container ctnAddMain;
    protected Picker datePicker;
    protected NumericSpinner txtSpinnerTerm, txtSpinnerRooms;
    Resources theme;

    public AddBooking(int property_id, double property_price, int property_nb_rooms, Form propertyDetailForm) {
        theme = UIManager.initFirstTheme("/theme");
        formAdd = new Form("Book For A While", BoxLayout.y());
        ctnAddMain = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Container ctnDate = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctnTerm_NbRooms = new Container(new BoxLayout(BoxLayout.X_AXIS));
//        Container ctnNbRooms = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctnBtns = new Container(new BoxLayout(BoxLayout.X_AXIS));

        datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);

        txtSpinnerTerm = new NumericSpinner();
        txtSpinnerTerm.setMin(1);
        txtSpinnerTerm.setMax(366);

        txtSpinnerRooms = new NumericSpinner();
        txtSpinnerRooms.setMin(1);
        txtSpinnerRooms.setMax(property_nb_rooms + 1);

        Button btnSubmit = new Button("Book");
        btnSubmit.addActionListener((e) -> {
            validateAndAddBooking(property_id, property_price, theme, false, propertyDetailForm);
        });

        Button btnGiftAnon = new Button("Gift Anonymously");
        btnGiftAnon.addActionListener((e) -> {
            validateAndAddBooking(property_id, property_price, theme, true, propertyDetailForm);
        });

        datePicker.setDate(new Date());

        ctnDate.add("Reservation Date");
        ctnDate.add(datePicker);

        ctnTerm_NbRooms.add("Term");
        ctnTerm_NbRooms.add(txtSpinnerTerm);

        ctnTerm_NbRooms.add(txtSpinnerRooms);
        ctnTerm_NbRooms.add("Number of Rooms");

        ctnBtns.add(btnSubmit);
        ctnBtns.add(btnGiftAnon);

        ctnAddMain.add(ctnDate);
        ctnAddMain.add(ctnTerm_NbRooms);
//        ctnAddMain.add(ctnNbRooms);
        ctnAddMain.add(ctnBtns);

        ctnAddMain.setScrollableX(true);

        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        Command goBackCmd = new Command("", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                propertyDetailForm.showBack();
            }
        };

        formAdd.getToolbar().addCommandToRightBar(goBackCmd);

        formAdd.add(ctnAddMain);
    }

    public Form getFormAdd() {
        return formAdd;
    }

    private void validateAndAddBooking(int property_id, double property_price, Resources theme, boolean isGift, Form propertyDetailForm) {
        // check for date after or equals today
        // note : during tests equals did not work (potientally related to extreme lowest time and condition time
        // maybe difference in seconds between execution, submit and pick date time
        if (datePicker.getDate().getTime() >= new Date().getTime()) {
            if (new BookingService().getFreeRooms(property_id, datePicker.getDate(), (int) txtSpinnerTerm.getValue())
                    >= (int) txtSpinnerRooms.getValue()) {
                Booking booking = new Booking();
                Property property = new Property();

                property.setId(property_id);

                booking.setBookingDate(datePicker.getDate());
                booking.setTerm((int) txtSpinnerTerm.getValue());
                booking.setNbr_rooms_reserved((int) txtSpinnerRooms.getValue());
                booking.setTotal_amount(property_price * booking.getTerm() * booking.getNbr_rooms_reserved());

                booking.setProperty(property);

                if (new BookingService().addBooking__GiftAnon(booking, isGift)) {
                    Dialog.show("Notice", "Reservation Successfull", "OK", null);
                    new UserBookingList__PropertyGiftList(property_id, isGift, propertyDetailForm).getFormList().show();
                } else {
                    Dialog.show("Error", "An Error Occured, Please Try Again Later", "OK", null);
                }
            } else {
                Dialog.show("Error", "No Open Bookings Were Found Based On The Filled Fields", "OK", null);
            }
        } else {
            Dialog.show("Error", "The Booking Date Have To Higher Than Today", "OK", null);
        }
    }
}
