/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.services;

import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.facebook.User;
import com.codename1.facebook.FBObject;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import com.hostandguest.views.Loginform;
import com.hostandguest.views.UserinterfaceForm;
import com.hostandguest.views.getMyPropertyForm;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Map;

/**
 *
 * @author Asus
 */

public class loginfacebook {
        private Resources theme;
                String clientId = "791297561038325";
                String redirectURI = "http://www.this-page-intentionally-left-blank.org/";
                String clientSecret = "94f1aeb64d2119df90d1653fec1482c4";
                Login fb2 = FacebookConnect.getInstance();
                String namestr ;
                String emailstr ;
                String lastnamestr ;
                String firstnamestr ;
        //use your own facebook app identifiers here   
                //These are used for the Oauth2 web login process on the Simulator.
    public loginfacebook (){
    }
    
    public void connectfacebook (){
        loginfacebook fcb = new loginfacebook() ;
        Login fb1 = FacebookConnect.getInstance();
        fb1.setClientId(clientId);
        
        fb1.setRedirectURI(redirectURI);
        fb1.setClientSecret(clientSecret);
        LoginCallback login = new LoginCallback() {
            @Override
            public void loginSuccessful(){
                String token = fb1.getAccessToken().getToken();
                // call methods to fetch data + add user to database...
                fetchData(token);
                UserService  us= new UserService() ;
                // call localhost to check if User Id exist
                if(us.check(namestr)){
                    Form f = new Form("hello facebook");
                    f.show();
                }
                else
                    System.out.println("wast able !!");
                us.adduser(namestr, namestr, emailstr, lastnamestr, firstnamestr);
                // added to get user current in case of facebook login
                ConnectionRequest con = new ConnectionRequest();
                NetworkManager.getInstance().addToQueueAndWait(con);
                us.getuseridonconnexion(new String(con.getResponseData()));
                getMyPropertyForm gmf = new getMyPropertyForm(theme, com.hostandguest.entities.User.currentUser);
                gmf.showMyProperties();

            }
            @Override
            public void loginFailed(String errorMessage){
                
            }
        };
        fb1.setCallback(login);
        if(!fb1.isUserLoggedIn()){
            fb1.doLogin();
        }
    }
    public void fetchData(String token) {
		User u ;
                String id = null;
		 String email = null, name , first_name = null, last_name = null;
                ConnectionRequest req = new ConnectionRequest() {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                com.hostandguest.entities.User usercourant = new com.hostandguest.entities.User ();
                JSONParser parser = new JSONParser();
                Map<String, Object> parsed = parser.parseJSON(new InputStreamReader(input, "UTF-8"));
               // int id = Integer.parseInt((String)parsed.get("id"));
                String id = (String) parsed.get("id");
                String email = (String) parsed.get("email");
                String name = (String) parsed.get("name");
                String first_name = (String) parsed.get("first_name");
                String last_name = (String) parsed.get("last_name");
                //chahine-bader_wow@hotmail.com
                String id1 = id.substring(0,4);
                String id2 = id.substring(4,8);
                String id3 = id.substring(8,12);
                String id4 = id.substring(8,id.length());
                usercourant.setFirst_name(first_name);
                usercourant.setLast_name(last_name);
                usercourant.setUsername(name);

                UserService  us= new UserService() ;
                namestr = name ;
                emailstr = email ;
                lastnamestr = last_name;
                firstnamestr = first_name;
                              
            }

        };
        req.setPost(false);
        req.setUrl("https://graph.facebook.com/v2.8/me");
        req.addArgumentNoEncoding("access_token", token); //this statement is used to patch access token with url
        req.addArgumentNoEncoding("fields", "id,email,name,first_name,last_name");
        //above statement is used to provide permission through url so server send data with respect ot permissions.
        NetworkManager.getInstance().addToQueueAndWait(req);
		
    }
}
