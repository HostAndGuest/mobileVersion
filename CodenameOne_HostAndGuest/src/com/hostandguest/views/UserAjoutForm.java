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
import com.hostandguest.services.UserService;
import java.io.IOException;





/**
 *
 * @author Asus
 */
class UserAjoutForm {
        Form Userajoutform;
    public UserAjoutForm () throws IOException 
    {
        Userajoutform = new Form("", BoxLayout.y());
        Container ctn1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container ctn11 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn4 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn5 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn6 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn7 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            
            Label newaccoutn = new Label ("Nouveau compte");
            Label lblusername = new Label("Username :" );
            Label lblpassword = new Label("Password : " );
            Label lblemail = new Label("email" );
            Label lbllastname = new Label("last name " );
            Label lblfirstname = new Label("first name " );
            
            TextField usernamefield = new TextField ("","username");           
            TextField passwordfield = new TextField ("","password",20,TextField.PASSWORD );
            TextField emailfield = new TextField("","email",40,TextField.EMAILADDR);
            TextField firstnamefield = new TextField ("","firstname");
            TextField lastnamefield = new TextField ("","lastname" );
            
            Button submit = new Button (Image.createImage("file:///C:/Users/Asus/Documents/NetBeansProjects/HostAndGuest_mobileVersion/CodenameOne_HostAndGuest/src/ressources/submit.jpg"));
            submit.getUnselectedStyle().setMargin(50, 50, 300, 50);
            Userajoutform.getUnselectedStyle().setBgColor(999999999,false);
            lblusername.getUnselectedStyle().setBgColor(999999999,false);
            lblpassword.getUnselectedStyle().setBgColor(999999999,false);
            lblemail.getUnselectedStyle().setBgColor(999999999,false);
            lbllastname.getUnselectedStyle().setBgColor(999999999,false);
            lblfirstname.getUnselectedStyle().setBgColor(999999999,false);
            newaccoutn.getUnselectedStyle().setMargin(0, 0, 350, 0);
            newaccoutn.getUnselectedStyle().setBgColor(999999999,false);
            
            ctn1.add(ctn11);
            ctn1.add(ctn2);
            ctn1.add(ctn3);
            ctn1.add(ctn4);
            ctn1.add(ctn5);
            ctn1.add(ctn6);
            ctn1.add(ctn7);
            
            ctn11.add(newaccoutn);
            
            ctn2.add(lblusername);
            ctn2.add(usernamefield);
            
            ctn3.add(lblpassword);
            ctn3.add(passwordfield);
            
            ctn4.add(lblemail);
            ctn4.add(emailfield);
            
            ctn5.add(lblfirstname);
            ctn5.add(firstnamefield);
            
            ctn6.add(lbllastname);
            ctn6.add(lastnamefield);
            
            ctn7.add(submit);

            
            
            Userajoutform.add(ctn1);
            
            
            Userajoutform.show();
            submit.addActionListener(e->{
                UserService service = new UserService();
                if ((usernamefield.getText().length()!=0) && (passwordfield.getText().length()!=0) && 
                        (emailfield.getText().length()!=0) &&  (lastnamefield.getText().length()!=0) 
                        &&  (firstnamefield.getText().length()!=0 ) )
                {

                    try {
                        service.adduser(usernamefield.getText(), passwordfield.getText(), emailfield.getText(), lastnamefield.getText(), firstnamefield.getText());
                        Loginform log = new Loginform(); 
                        log.showloginform();
                    } catch (IOException ex) {

                    }

                }
                else {
                        Dialog.show("erreur de saisie", "erreur de saisie veuillez ne laisser aucun champ vide", "Ok", null);
                        }
            });
            Command cmdback = new Command("Back");
            Userajoutform.getToolbar().addCommandToLeftBar(cmdback);
            Userajoutform.addCommandListener(e->{
            try {
                Loginform logback = new Loginform() ;
                logback.showloginform();
            } catch (IOException ex) {

            }
                        
        });

    }
    
    public void showajoutform ()
    {
        Userajoutform.show();
    }
    
}
