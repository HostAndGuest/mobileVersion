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
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.hostandguest.services.UserCourant;
import com.hostandguest.services.UserService;
import java.io.IOException;



/**
 *
 * @author Asus
 */
public class UserModificationForm {
     Form Usermodificationform ;
    private Resources theme;
    public UserModificationForm () throws IOException
    {
        Usermodificationform = new Form("Modification panel", BoxLayout.y());
        Container ctn1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container ctn2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn4 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn5 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn6 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn7 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn8 = new Container(new BoxLayout(BoxLayout.X_AXIS));

            Label lblusername = new Label("username:" );
            Label lbloldpassword = new Label("old password:" );
            Label lblnewpassword = new Label("new password:" );
            Label lblrepeatpassword = new Label("repeat new password:" );
            Label lblfirstname = new Label("first name:" );
            Label lbllastname = new Label("last name:" );
            
            TextField usernamefield = new TextField ("","",20, TextField.UNEDITABLE);
            usernamefield.setEditable(false);
            TextField oldpasswordfield = new TextField ("", "", 20, TextField.PASSWORD);
            TextField newpasswordfield = new TextField ("", "", 20, TextField.PASSWORD );
            TextField repeatpasswordfield = new TextField ("", "", 20, TextField.PASSWORD );
            TextField firstnamefield = new TextField ("" );
            TextField lastnamefield = new TextField ("" );
            Button Submit = new Button (Image.createImage("file:///C:/Users/Asus/Documents/NetBeansProjects/HostAndGuest_mobileVersion/CodenameOne_HostAndGuest/src/ressources/submit.jpg"));
            //Button Menu = new Button ("main menu");
            Submit.getUnselectedStyle().setMargin(50, 50, 300, 50);
            Usermodificationform.getUnselectedStyle().setBgColor(999999999,false);
            lblusername.getUnselectedStyle().setBgColor(999999999,false);
            lbloldpassword.getUnselectedStyle().setBgColor(999999999,false);
            lblnewpassword.getUnselectedStyle().setBgColor(999999999,false);
            lblrepeatpassword.getUnselectedStyle().setBgColor(999999999,false);
            lblfirstname.getUnselectedStyle().setBgColor(999999999,false);
            lbllastname.getUnselectedStyle().setBgColor(999999999,false);
            
            ctn1.add(ctn2);
            ctn1.add(ctn3);
            ctn1.add(ctn4);
            ctn1.add(ctn5);
            ctn1.add(ctn6);
            ctn1.add(ctn7);
            ctn1.add(ctn8);
            
            ctn2.add(lblusername);
            ctn2.add(usernamefield);
            
            ctn3.add(lbloldpassword);
            ctn3.add(oldpasswordfield);
            
            ctn4.add(lblnewpassword);
            ctn4.add(newpasswordfield);
            
            ctn5.add(lblrepeatpassword);
            ctn5.add(repeatpasswordfield);
            
            ctn6.add(lblfirstname);
            ctn6.add(firstnamefield);
            
            ctn7.add(lbllastname);
            ctn7.add(lastnamefield);
            
            ctn8.add(Submit);
          //  ctn1.add(Menu);
            
            
            Usermodificationform.add(ctn1);
            
            
            Usermodificationform.show();
            System.out.println(UserCourant.getCurrentuser().getUsername());
            
            usernamefield.setText(UserCourant.getCurrentuser().getUsername());
            lastnamefield.setText(UserCourant.getCurrentuser().getLast_name());
            firstnamefield.setText(UserCourant.getCurrentuser().getFirst_name());
            
            
            Submit.addActionListener(e->{
                UserService moduser = new UserService ();
                if ((usernamefield.getText().length()!=0) && (oldpasswordfield.getText().length()!=0)
                        &&  (newpasswordfield.getText().length()!=0)
                        &&  (lastnamefield.getText().length()!=0) 
                        &&  (firstnamefield.getText().length()!=0 )
                        &&  (newpasswordfield.getText()!=oldpasswordfield.getText()))
                moduser.updateuser(oldpasswordfield.getText(), newpasswordfield.getText(), repeatpasswordfield.getText(), lastnamefield.getText(), firstnamefield.getText());
                else 
                {
                    Dialog.show("modification erroné", "old password and new password are the same please retry", "Ok", null);
                    }
                
            });
            Command cmdback = new Command("Menu");
            Usermodificationform.getToolbar().addCommandToLeftBar(cmdback);
            Usermodificationform.addCommandListener(e->{
            
                getMyPropertyForm gmf = new getMyPropertyForm(theme, com.hostandguest.entities.User.currentUser);
                gmf.showMyProperties();
  
                        
        });

    }
    
    public void showusermodform ()
    {
        Usermodificationform.show();
    }
    
}
