/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.hostandguest.entities.Review;
import com.hostandguest.services.ReviewService;
import com.hostandguest.util.NoOpenReviewReservationsException;
import java.util.List;

/**
 *
 * @author Unlucky
 */
public class ReviewList {

    Container ctnList;
    Form formList;
    Resources theme;
    int property_id;
    List<Review> reviews;

    public ReviewList(int property_id, Form propertyDetailForm) {
        theme = UIManager.initFirstTheme("/theme");
        UIBuilder uib = new UIBuilder();

        ctnList = uib.createContainer(theme, "reviewList");
        formList = ctnList.getComponentForm();

        this.property_id = property_id;

        refreshReviews();

        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_RATE_REVIEW, s);
        Command addReviewCmd = new Command("Leave A Review", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    ReviewAdd reviewAdd = new ReviewAdd(theme, property_id, propertyDetailForm, formList);
                    reviewAdd.getFormAdd().show();
                } catch (NoOpenReviewReservationsException ex) {
                    Dialog.show("Error", ex.getMessage(), "OK", null);
                }
            }
        };

        icon = FontImage.createMaterial(FontImage.MATERIAL_TRENDING_UP, s);
        Command viewStatsCmd = new Command("View Statistics", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!reviews.isEmpty()) {
                    new GenerateReviewStats(reviews, formList).getFormStats().show();
                } else {
                    Dialog.show("Notice", "No Available Reviews To View Statistics", "OK", null);
                }
            }
        };

        icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        Command goBackCmd = new Command("", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                propertyDetailForm.showBack();
            }
        };
        
        formList.getToolbar().addCommandToRightBar(addReviewCmd);
        formList.getToolbar().addCommandToRightBar(viewStatsCmd);
        formList.getToolbar().addCommandToRightBar(goBackCmd);
    }

    protected void refreshReviews() {
        reviews = new ReviewService().getPropertyReviews(property_id);

        for (Review review : reviews) {
            ReviewModel reviewModel = new ReviewModel(theme, review, this);
            ctnList.add(reviewModel.getCtnModel());
        }
    }

    public Form getFormList() {
        return formList;
    }

}
