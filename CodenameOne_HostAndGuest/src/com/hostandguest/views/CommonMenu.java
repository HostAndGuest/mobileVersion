/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import com.hostandguest.entities.User;
import com.hostandguest.services.UserCourant;
import java.io.IOException;

/**
 *
 * @author Unlucky
 */
public class CommonMenu {

    public CommonMenu(Resources theme, Form targetForm) {
        Command cmdMy = new Command("My Properties");
        Command cmdAll = new Command("All Properties");
        Command cmdGmap = new Command("H&G - Map");
        Command cmdUseerBookings = new Command("My Bookings");
        Command cmdprofile = new Command("Edit profile");
        Command cmdchat = new Command("Chat");
        Command cmddisconnect = new Command("Disconnect");
        
        targetForm.getToolbar().addCommandToOverflowMenu(cmdMy);
        targetForm.getToolbar().addCommandToOverflowMenu(cmdAll);
        targetForm.getToolbar().addCommandToOverflowMenu(cmdGmap);
        targetForm.getToolbar().addCommandToOverflowMenu(cmdUseerBookings);
        targetForm.getToolbar().addCommandToOverflowMenu(cmdprofile);
        targetForm.getToolbar().addCommandToOverflowMenu(cmdchat);
        targetForm.getToolbar().addCommandToOverflowMenu(cmddisconnect);
        
        targetForm.addCommandListener(e -> {
            if (e.getCommand() == cmdMy) {
                getMyPropertyForm MyPropForm = new getMyPropertyForm(theme, User.currentUser);
                MyPropForm.showMyProperties();
            } else if (e.getCommand() == cmdGmap) {
                GoogleMapForm Gform = new GoogleMapForm(theme);
                Gform.showGoogleMap();
            } else if (e.getCommand() == cmdAll) {
                getAllPropertyForm AllPropForm = new getAllPropertyForm(theme);
                AllPropForm.showAllProperties();
            } else if (e.getCommand() == cmdUseerBookings) {
                new UserBookingList__PropertyGiftList(1, false, null).getFormList().show();
            } else if (e.getCommand() == cmdprofile) {
                try {
                    UserModificationForm usermod = new UserModificationForm();
                    usermod.showusermodform();
                } catch (IOException ex) {
                    Log.e(ex);
                }
            } else if (e.getCommand() == cmdchat) {
                ChatList chat = new ChatList();
                chat.showList();
            } else if (e.getCommand() == cmddisconnect) {
                try {
                    UserCourant.setCurrentuser(null);
                    Loginform log = new Loginform();
                    log.showloginform();
                } catch (IOException ex) {
                    Log.e(ex);
                }
            }
        });
    }

}
