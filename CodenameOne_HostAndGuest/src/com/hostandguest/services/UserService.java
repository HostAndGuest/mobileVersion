/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.hostandguest.entities.User;
import java.io.IOException;
import java.util.Map;
//import com.codename1.facebook;


/**
 *
 * @author Asus
 */
public class UserService {
    private Resources theme;
    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

    }
    
    public boolean loginUser(String username,String password){

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/ScriptsHostAndGuest/Login.php?username="+username+"&password="+password); 
        NetworkManager.getInstance().addToQueueAndWait(con);
        System.out.println(new String(con.getResponseData()));
        boolean var = userexistant(new String(con.getResponseData())) ;
        System.out.println(var);
         if (userexistant(new String(con.getResponseData()))){
                  System.out.println("ok");
                  getuseridonconnexion(new String(con.getResponseData()));
                  return true ;
         }
         else 
             
              Dialog.show("erreur", "username ou password erroné", "Ok", null);
         return false;
  
    }
    public boolean userexistant(String json) {
         boolean comptevalide = false ;
         System.out.println("we entred the json");
        try {

            JSONParser j = new JSONParser();

            Map<String, Object> users = j.parseJSON(new CharArrayReader(json.toCharArray()));
           if(!users.isEmpty())
           {
               comptevalide = true ;
           }

        } catch (IOException ex) {
         }
        return comptevalide ;

    }

    public void Redirecttocreateaccount() {
        UIBuilder ui = new UIBuilder ();
        Container ctn = ui.createContainer(theme, "GUIAddAccount");
         Form f = ctn.getComponentForm();
         TextField username = (TextField) ui.findByName("TextField", ctn);
         TextField password = (TextField) ui.findByName("TextField1", ctn);
         TextField email = (TextField) ui.findByName("TextField2", ctn);
         TextField lastname = (TextField) ui.findByName("TextField3", ctn);
         TextField firstname = (TextField) ui.findByName("TextField4", ctn);
         Label lhostandguestaccount = (Label) ui.findByName("Label", ctn);
         Label lusername = (Label) ui.findByName("Label1", ctn);
         Label lpassword = (Label) ui.findByName("Label2", ctn);
         Label lemail = (Label) ui.findByName("Label3", ctn); 
         Label llastname = (Label) ui.findByName("Label4", ctn);
         Label lfirstname = (Label) ui.findByName("Label5", ctn);
         Button button = (Button) ui.findByName("Button", ctn);
         Button button1 = (Button) ui.findByName("Button1", ctn);
         f.show();
         button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://localhost/ScriptsHostAndGuest/insert.php?username=" + username.getText() + "&password=" + password.getText() + "&email=" + email.getText() + "&lastname" +lastname.getText()+"firstname"+firstname.getText());

                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "ajout ok", "Ok", null);
                        }
                    }
                });
                
                NetworkManager.getInstance().addToQueue(req);
                

            }
        });

        f.show();

    
    }
             public User Getuserinformations (String json )
                 {
                     User userinfo =new User ();
                     try {

            JSONParser j = new JSONParser();

            Map<String, Object> users = j.parseJSON(new CharArrayReader(json.toCharArray()));

            System.out.println();
            java.util.Map<String, Object> content = (java.util.Map<String, Object>)users.get("user");

             {

                userinfo.setId(Integer.parseInt(content.get("id").toString()));
                userinfo.setFirst_name(content.get("firstname").toString());
                userinfo.setLast_name(content.get("lastname").toString());
                System.out.println(userinfo.getId());
                User.currentUser=userinfo.getId();
                UserCourant.setCurrentuser(userinfo);
                System.out.println(UserCourant.getCurrentuser());
                
                

 

            }

        } catch (IOException ex) {
         }
                     return userinfo ;
                 }
             public void adduser (String username,String password,String email,String lastname,String firstname)
             {
                ConnectionRequest req = new ConnectionRequest();
                 req.setUrl("http://localhost/ScriptsHostAndGuest/insert.php?username=" + username + "&password=" + password + "&email=" + email+ "&lastname=" +lastname+"&firstname="+firstname);
                 req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "ajout ok", "Ok", null);
                         //   NetworkManager.getInstance().addToQueue(req);
                        }
                    }
                });
                 NetworkManager.getInstance().addToQueue(req);
             }
             public void updateuser (String oldpassword,String newpassword,String confirmpassword,String lastname,String firstname)
             {
                 if (!oldpassword.equals(newpassword)&&newpassword.equals(confirmpassword)){
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/ScriptsHostAndGuest/updateuser.php?oldpassword="+oldpassword+"&lastname="+lastname+"&firstname="+firstname+"&newpassword="+newpassword+"&id="+UserCourant.getCurrentuser().getId());
                    con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                String s = new String(con.getResponseData());
                        if (s.startsWith("success")) {
                        Dialog.show("Confirmation", "update done", "Ok", null);
                        }
                        else 
                        Dialog.show("error", "update not done ", "ok", null);

              }
            
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
             }
         
             }
          public int getuseridonconnexion (String json )
         {
             User usercourant = new User();
            try {
            JSONParser j = new JSONParser();

            Map<String, Object> users = j.parseJSON(new CharArrayReader(json.toCharArray()));

            System.out.println();
            java.util.Map<String, Object> content = (java.util.Map<String, Object>)users.get("user");
             {
                usercourant.setId(Integer.parseInt(content.get("id").toString()));
                usercourant.setFirst_name(content.get("firstname").toString());
                usercourant.setLast_name(content.get("lastname").toString());
                usercourant.setUsername(content.get("username").toString());
                System.out.println(usercourant.getId());
                User.currentUser=usercourant.getId();
                UserCourant.setCurrentuser(usercourant);
                System.out.println(UserCourant.getCurrentuser());

            }

        } catch (IOException ex) {
         }                   
             return usercourant.getId();
         }

    public boolean check(String name) {
        boolean userexists = false ;
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/ScriptsHostAndGuest/check.php?username="+name); 
        NetworkManager.getInstance().addToQueueAndWait(con);
    //    boolean var = userexistant(new String(con.getResponseData())) ;
        if (userexistant(new String(con.getResponseData()))){
            System.out.println("user exist");
            // we will insert here tge current use id whatever if exists or not 
            userexists= true ;
                  return userexists ;
         }
         else 
             
              System.out.println("lets create an account fb !");
              return userexists;

    }
}
