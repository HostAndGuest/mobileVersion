/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.hostandguest.entities.Booking;
import com.hostandguest.entities.Review;
import com.hostandguest.services.ReviewService;
import com.hostandguest.util.NoOpenReviewReservationsException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class ReviewAdd {
    protected Form formAdd;
    protected Container ctnAdd;
    protected TextArea txtComment;
    protected ComboBox cbBookings;
    protected Slider sliderPQ, sliderPrecision, sliderCom, sliderClean, sliderPos;
    protected Button btnSubmit, btnUploadVid;
    
    public ReviewAdd(Resources theme, int property_id, Form propertyDetailForm, Form listReviewsForm) throws NoOpenReviewReservationsException {
        UIBuilder uib = new UIBuilder();
        ctnAdd = uib.createContainer(theme, "ReviewAdd");
        formAdd = ctnAdd.getComponentForm();
        
        txtComment = (TextArea) uib.findByName("txtComment", ctnAdd);
        sliderPQ = (Slider) uib.findByName("sliderPQ", ctnAdd);
        sliderPrecision = (Slider) uib.findByName("sliderPrecision", ctnAdd);
        sliderCom = (Slider) uib.findByName("sliderCom", ctnAdd);
        sliderClean = (Slider) uib.findByName("sliderClean", ctnAdd);
        sliderPos = (Slider) uib.findByName("sliderPos", ctnAdd);
        cbBookings = (ComboBox) uib.findByName("cbBookings", ctnAdd);
        btnSubmit = (Button) uib.findByName("btnSubmit", ctnAdd);
        btnUploadVid = (Button) uib.findByName("btnUploadVid", ctnAdd);
        
        // setting ratings
        new RatingSlider().createStarRankSlider(sliderPQ);
        new RatingSlider().createStarRankSlider(sliderPrecision);
        new RatingSlider().createStarRankSlider(sliderCom);
        new RatingSlider().createStarRankSlider(sliderClean);
        new RatingSlider().createStarRankSlider(sliderPos);

        // due to
        Label lblFilePath = new Label("");
        
        List<Booking> openReservations = new ReviewService().getOpenBookings(property_id);
        
        if (openReservations.isEmpty())
        {
            throw new NoOpenReviewReservationsException("No Bookings Left For To Review");
        }
        else
        {
            btnUploadVid.addActionListener((evt) -> {
                ActionListener callback = e->{
                if (e != null && e.getSource() != null) {
                    String filePath = (String)e.getSource();
                    lblFilePath.setText(filePath);
                }
            };
            
            if (FileChooser.isAvailable()) {
                 FileChooser.showOpenDialog(".mp4,video/mp4", callback);
            } else {
                Display.getInstance().openGallery(callback, Display.GALLERY_VIDEO);
            }
            });
            
            // date format
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        
            for (Booking booking : openReservations)
            {
                cbBookings.addItem(dateFormat.format(booking.getBookingDate()));
            }
            
            btnSubmit.addActionListener((evt) -> {
                try
                {   
                    // call review service
                    if (!txtComment.getText().equals("") && txtComment.getText() != null)
                    {
                        Review review = new Review();
                        
                        // upload video if chose to upload
                        if (!lblFilePath.getText().equals(""))
                        {
                            new ReviewService().uploadVideo(lblFilePath.getText());
                            review.setVideo_url("http://localhost/host_and_guest_webservices/files/" + new ReviewService().getFutureID() + ".mp4");
                        }
                        
                        review.setComment(txtComment.getText());
                        review.setPrice_quality(sliderPQ.getProgress());
                        review.setLieu(sliderPos.getProgress());
                        review.setPrecision(sliderPrecision.getProgress());
                        review.setCommunication(sliderCom.getProgress());
                        review.setCleanliness(sliderClean.getProgress());
                        
                        /* comment date is set in the webservice */
                        /* satisfaction is set in the service */
                        
                        review.setBooking(openReservations.get(
                            cbBookings.getSelectedIndex()
                        ));
                        
                        if (new ReviewService().add(review, !lblFilePath.getText().equals("")))
                        {
                            formAdd.removeAll();
                            new ReviewList(property_id, propertyDetailForm).getFormList().show();
                        }
                        else
                        {
                            Dialog.show("Review Not Added", "An Error Occured", "OK", null);
                        }
                    }
                    else
                        Dialog.show("Notice", "Please provide a comment", "OK", null);
                }
                catch (IndexOutOfBoundsException err)
                {
                    Log.e(err);
                }
            });
        }
        
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ASSIGNMENT_RETURN, s);
        Command goBackCmd = new Command("", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listReviewsForm.showBack();
            }
        };
        
        formAdd.getToolbar().addCommandToRightBar(goBackCmd);
    }

    public Form getFormAdd() {
        return formAdd;
    }
}
