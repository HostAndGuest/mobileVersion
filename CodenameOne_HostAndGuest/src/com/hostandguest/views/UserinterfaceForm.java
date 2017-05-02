/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.hostandguest.services.UserCourant;
import java.io.IOException;



/**
 *
 * @author Asus
 */
public class UserinterfaceForm {
    Form Userpanelform ;
    public UserinterfaceForm () throws IOException
    {
        Userpanelform = new Form("User Panel", BoxLayout.y());
        Container ctn1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container ctn2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn4 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn5 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn6 = new Container(new BoxLayout(BoxLayout.X_AXIS));

            Label lblusername = new Label("hello " );
            Label lblgetname = new Label(" " );
            Button Myproperties = new Button ("My properties");
            Button MyExperiences = new Button ("My Experiences");
            Button Parametres = new Button ("Parametres");
            Button Reviews = new Button ("Reviews");
            Userpanelform.getUnselectedStyle().setBgColor(999999999,false);
            ctn1.getUnselectedStyle().setMargin(50, 50, 50, 50);
            ctn1.getUnselectedStyle().setMarginLeft(150);
           // Myproperties.getUnselectedStyle().setOpacity(200);
            lblgetname .getUnselectedStyle().setBgColor(999999999,false); 
            lblusername.getUnselectedStyle().setBgColor(999999999,false);
            lblusername.getUnselectedStyle().setMargin(0, 50, 0, 0);
            lblgetname.getUnselectedStyle().setMargin(0, 50, 0, 0);
           // Myproperties.getUnselectedStyle().setPadding(0,0,0,0);
            Myproperties.getUnselectedStyle().setMargin(50, 50, 0, 50);
            MyExperiences.getUnselectedStyle().setMargin(50, 50, 0, 50);
            Parametres.getUnselectedStyle().setMargin(50, 50, 0, 50);
            Reviews.getUnselectedStyle().setMargin(50, 50, 0, 50);
           // lblusername.setWidth(10);
            ctn1.add(ctn2);
            ctn2.add(lblusername);
            ctn2.add(lblgetname);
            ctn1.add(Myproperties);
            ctn1.add(MyExperiences);
            ctn1.add(Reviews);
            ctn1.add(Parametres);
            
            Userpanelform.add(ctn1);
            lblgetname.setText(UserCourant.getCurrentuser().getUsername());
            
            
            Userpanelform.show();
            
         //   lblgetname.setText(UserCourant.getCurrentuser().getUsername());
            Parametres.addActionListener(e->{
            try {
                UserModificationForm usermod = new UserModificationForm();
                usermod.showusermodform();
            } catch (IOException ex) {

            }
            });

           Command cmdback = new Command("Disconnect");
           Userpanelform.getToolbar().addCommandToRightBar(cmdback);
           Userpanelform.addCommandListener(e->{
           try {
                UserCourant.setCurrentuser(null);
                Loginform log = new Loginform();
                log.showloginform();
            } catch (IOException ex) {

            }
            
        });

    }
    
    public void showuserpanelform ()
    {
        Userpanelform.show();
    }
    
}
