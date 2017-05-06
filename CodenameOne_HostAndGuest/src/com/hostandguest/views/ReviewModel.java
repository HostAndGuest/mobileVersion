/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.components.ImageViewer;
import com.codename1.components.MediaPlayer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.hostandguest.entities.Review;
import com.hostandguest.services.ReviewService;
import com.hostandguest.services.UserCourant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Unlucky
 */
public class ReviewModel {

    protected Form modelForm;
    protected Container ctnModel;
    protected Label lblUserName, lblCmntDate, lblBknDate, lblComment;
    protected Slider sliderPQ, sliderPrecision, sliderCom, sliderClean, sliderPos;
    protected Button btnDelete;    
    protected ImageViewer ivSatisfaction, ivMood;
    // set through code
    protected MediaPlayer mpVideo;;
    
    public ReviewModel(Resources theme, Review review, ReviewList reviewListInstance) {
        UIBuilder.registerCustomComponent("ImageViewer", ImageViewer.class);
        UIBuilder.registerCustomComponent("MediaPlayer", MediaPlayer.class);
        
        UIBuilder uib = new UIBuilder();
        ctnModel = uib.createContainer(theme, "reviewModel");
        modelForm = ctnModel.getComponentForm();
        
        Container ctnVideo = (Container) uib.findByName("ctnVideo", ctnModel);
        
        lblUserName = (Label) uib.findByName("lblUserName", modelForm);
        lblCmntDate = (Label) uib.findByName("lblCmntDate", modelForm);
        lblBknDate = (Label) uib.findByName("lblBknDate", modelForm);
        lblComment = (Label) uib.findByName("lblComment", modelForm);
        
        sliderPQ = (Slider) uib.findByName("sliderPQ", ctnModel);
        sliderPrecision = (Slider) uib.findByName("sliderPrecision", ctnModel);
        sliderCom = (Slider) uib.findByName("sliderCom", ctnModel);
        sliderClean = (Slider) uib.findByName("sliderClean", ctnModel);
        sliderPos = (Slider) uib.findByName("sliderPos", ctnModel);
        
        ivSatisfaction = (ImageViewer) uib.findByName("ivSatisfaction", ctnModel);
        ivMood = (ImageViewer) uib.findByName("ivMood", ctnModel);
        
        btnDelete = (Button) uib.findByName("btnDelete", modelForm);
        
        // date format
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        
        lblUserName.setText(review.getBooking().getGuest().getLast_name() + " " + review.getBooking().getGuest().getFirst_name());
        lblCmntDate.setText("Comment Date - " + dateFormat.format(review.getDateComment()));
        lblBknDate.setText("Booking Date - " + dateFormat.format(review.getBooking().getBookingDate()));
        lblComment.setText(review.getComment());
        
        // setting ratings
        new RatingSlider().createStarRankSlider(sliderPQ);
            sliderPQ.setProgress(review.getPrice_quality());
            sliderPQ.setEditable(false);
        new RatingSlider().createStarRankSlider(sliderPrecision);
            sliderPrecision.setProgress(review.getPrecision());
            sliderPrecision.setEditable(false);
        new RatingSlider().createStarRankSlider(sliderCom);
            sliderCom.setProgress(review.getCommunication());
            sliderCom.setEditable(false);
        new RatingSlider().createStarRankSlider(sliderClean);
            sliderClean.setProgress(review.getCleanliness());
            sliderClean.setEditable(false);
        new RatingSlider().createStarRankSlider(sliderPos);
            sliderPos.setProgress(review.getLieu());
            sliderPos.setEditable(false);
        
        if (!review.getVideo_url().equals("{}"))
        {
            Style s = UIManager.getInstance().getComponentStyle("Title");
            
            // only works audio
            // might require main container to be BorderLayout
            mpVideo = new MediaPlayer();
            mpVideo.setDataSource(review.getVideo_url());
            
            FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_PAUSE, s);
            mpVideo.setPauseIcon(icon);
            
            icon = FontImage.createMaterial(FontImage.MATERIAL_PLAY_ARROW, s);
            mpVideo.setPlayIcon(icon);
            
            icon = FontImage.createMaterial(FontImage.MATERIAL_FAST_FORWARD, s);
            mpVideo.setFwdIcon(icon);
            
            icon = FontImage.createMaterial(FontImage.MATERIAL_FAST_REWIND, s);
            mpVideo.setBackIcon(icon);
            
            ctnVideo.add(BorderLayout.CENTER, mpVideo);
        }
        
        // default is already set
        if (review.getMood() != 0 && review.getSatisfaction_level() != 0)
            ivSatisfaction.setImage(theme.getImage("chart_" + (review.getSatisfaction_level() + 1 ) + ".PNG"));
        
        switch (review.getMood())
        {
            case 1:
                ivMood.setImage(theme.getImage("happyface.png"));
                break;
            case 2:
                ivMood.setImage(theme.getImage("sadface.png"));
                break;
            case 3:
                ivMood.setImage(theme.getImage("angryface.png"));
                break;
            // default is already set pokerface (undetermined satisfaction)
        }
        
        // handling the delete button
        // maybe add equals method for user based on id
        if (review.getBooking().getGuest().getId() != UserCourant.getCurrentuser().getId())
        {
            btnDelete.setVisible(false);
        }
        else
        {
            Style s = UIManager.getInstance().getComponentStyle("Title");
            FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_DELETE, s);
            btnDelete.setIcon(icon);
            
            btnDelete.addActionListener((evt) -> {
                if (new ReviewService().deleteReview(review.getId(), review.getBooking().getId()))
                {
                    Dialog.show("Suppression Information", "Successfully removed", "OK", null);
                    
                }
                else
                    Dialog.show("Suppression Information", "An Error Occured", "OK", null);
                
                reviewListInstance.getFormList().removeAll();
                reviewListInstance.refreshReviews();
            });
        }
    }

    public Form getModelForm() {
        return modelForm;
    }

    public Container getCtnModel() {
        return ctnModel;
    }
}
