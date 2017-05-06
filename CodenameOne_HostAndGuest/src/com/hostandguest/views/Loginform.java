/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.components.ImageViewer;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.hostandguest.services.UserService;
import com.hostandguest.services.loginfacebook ;
import java.io.IOException;



/**
 *
 * @author Asus
 */
public class Loginform {
        private Resources theme;
    Form loginform ;
    public Loginform () throws IOException
    {
        loginform = new Form("Host and Guest Login ", BoxLayout.y());

     //   Toolbar tb = new Toolbar(true);
      //  loginform.setToolbar(tb);
        
 /*       Style iconStyle = loginform.getUIManager().getComponentStyle("Title");
        FontImage leftArrow = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, iconStyle, 4);
        FontImage rightArrow = FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, iconStyle, 4); */
        
        Container ctn0 = new Container(new BoxLayout(BoxLayout.Y_AXIS)); 
        Container ctn1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn2 = new Container(new BoxLayout(BoxLayout.X_AXIS));

       
            Label lblusername = new Label("Username" );
            Label lblpassword = new Label("Password" );
            Label hostandgues= new Label("Welcome to Host&Guest" );
           

            TextField usernamefield = new TextField ("","Username" );
            TextField passwordfield = new TextField ("","Password",20,TextField.PASSWORD );
            Button connexion = new Button (Image.createImage("/connect-button.jpg"));
            Button ajout = new Button (Image.createImage("/newaccount.png"));
            Button facebook = new Button (Image.createImage("/facebooklogin.jpg"));
            ImageViewer img = new ImageViewer (Image.createImage("/hnglogo.jpg"));
            //loginform.setUIID(id);
            ImageViewer img1 = new ImageViewer (Image.createImage("/connect.jpg"));
           // Image bgimg = new Image ();
           lblusername.getUnselectedStyle().setBgColor(0xFFFFFF,false); 
           lblpassword.getUnselectedStyle().setBgColor(0xFFFFFF,false); 
            hostandgues.getUnselectedStyle().setFgColor(444444444);
            loginform.getUnselectedStyle().setBgColor(0xFFFFFF,false);
            ctn0.getUnselectedStyle().setMargin(50, 50, 50, 50);
            ctn2.getUnselectedStyle().setMarginLeft(150);
            hostandgues.getUnselectedStyle().setBgColor(0xFFFFFF);
            ajout.getUnselectedStyle().setOpacity(200);
            usernamefield.getUnselectedStyle().setBgColor(0xFFFFFF,false);
            passwordfield .getUnselectedStyle().setBgColor(0xFFFFFF,false); 
            facebook.getUnselectedStyle().setPadding(0,0,0,0);
            ajout.getUnselectedStyle().setPadding(0,0,0,0);
            connexion.getUnselectedStyle().setPadding(0,0,0,0);
            lblusername.getUnselectedStyle().setMargin(50, 50, 0, 0);
            lblpassword.getUnselectedStyle().setMargin(50, 50, 0, 0);
            lblusername.setWidth(10);
            connexion.setWidth(20);
            connexion.setSize(new Dimension(50,50));
            connexion.getUnselectedStyle().setBgColor(0xFFFFFF,false);
            connexion.getUnselectedStyle().setPadding(0, 0, 0, 0);
            //connexion.setPreferredSize(new Dimension(100,80));
           // connexion.setSize(20,20);
            ctn2.add(hostandgues);
            ctn2.add(img);
            ctn0.add(lblusername);
            ctn0.add(usernamefield);
            ctn0.add(lblpassword);
            ctn0.add(passwordfield);
            ctn0.add(connexion);
            ctn0.add(facebook);
            ctn0.add(ajout);
            
            //ctn0.add(img1);
            loginform.add(ctn2);
            loginform.add(ctn0);
            loginform.add(ctn1);
            
            
            loginform.show();
            connexion.addPointerReleasedListener(e->{
                   UserService  us = new UserService();
                   boolean isvalideaccount = us.loginUser(usernamefield.getText(), passwordfield.getText());
                   if (isvalideaccount)
                   {
                        getMyPropertyForm gmf = new getMyPropertyForm(theme, com.hostandguest.entities.User.currentUser);
                        gmf.showMyProperties();
                   }
                    else
                       Dialog.show("Error", "Incorrect Login/Password", "OK", null);
            });
            ajout.addActionListener(e->{
            try {
                UserAjoutForm userajout = new UserAjoutForm ();
                userajout.showajoutform();
            } catch (IOException ex) {
                Log.e(ex);
            }
                
            });
            facebook.addActionListener(e->{
                loginfacebook f = new loginfacebook();
                f.connectfacebook();
            });

    }
    
    public void showloginform () 
    {

        loginform.show();
    
    }
    
}
