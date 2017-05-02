/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.hostandguest.entities.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import com.hostandguest.entities.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

import java.net.URISyntaxException;




/**
 *
 * @author Marouane
 */
public class MessageService {
      public static final String ACCOUNT_SID = "AC6cc54963ca238618f6df28960080ab57";
    public static final String AUTH_TOKEN = "182caf96d9f1bce47daefc6029c0b384";
       
    public ArrayList<User> getUserList(){
        ArrayList<User> users = new ArrayList<User>();
                try
                {
                    
                    ConnectionRequest con = new ConnectionRequest("http://localhost/ScriptsHostAndGuest/getUserList.php?userId="+User.currentUser);
                    NetworkManager.getInstance().addToQueueAndWait(con);
                    
                    Map<String,Object> response = new JSONParser().parseJSON(new InputStreamReader(
                            new ByteArrayInputStream(con.getResponseData()), "UTF-8"));
                    
                    java.util.List<Map<String, Object>> responseContent = new ArrayList<>();
                    
                    try
                    {
                        responseContent = (java.util.List<Map<String, Object>>)response.get("users");
                    }
                    catch (ClassCastException e)
                    {
                        // when only one record is found causes above exception
                        responseContent.add((Map<String, Object>)response.get("users"));
                    }
                    
                    // making sure there is elements in the list
                    // isEmpty wont work on the list in this case
                    if (!responseContent.get(0).isEmpty())
                    {
                        for (Map<String, Object> record : responseContent)
                        {
                            User user = new User();
                            user.setId(Integer.valueOf(record.get("id").toString()));
                            user.setUsername(record.get("username").toString());
                            users.add(user);
                        }
                    }
                    
                    NetworkManager.getInstance().addToQueue(con);
                    
                }
                catch (IOException ex)
                {
                   // Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
                }
                return users;
    }

    public ArrayList<Message> getConversation(int user1, int user2){
        System.out.println("BGGGGGGGGGGGGGGGGGGGGGGGGGG");
          ArrayList<Message> messages = new ArrayList<Message>();
          System.out.println("BGGGGGGGGGGGGGGGGGGGGGGGGGG");
                try
                {
                    
                    ConnectionRequest con = new ConnectionRequest("http://localhost/ScriptsHostAndGuest/getMessages.php?user1="+user1+"&user2="+user2);
                    System.out.println(con.getUrl());
                    NetworkManager.getInstance().addToQueueAndWait(con);
                    if(con.getResponseData().toString()!= "Notice: Undefined variable: json in C:\\xampp\\htdocs\\ScriptsHostAndGuest\\getMessages.php on line 37\n" +
"null"){

                    Map<String,Object> response = new JSONParser().parseJSON(new InputStreamReader(
                            new ByteArrayInputStream(con.getResponseData()), "UTF-8"));
                    
                    java.util.List<Map<String, Object>> responseContent = new ArrayList<>();
                    
                    try
                    {
                        responseContent = (java.util.List<Map<String, Object>>)response.get("messages");
                    }
                    catch (ClassCastException e)
                    {
                        // when only one record is found causes above exception
                        responseContent.add((Map<String, Object>)response.get("messages"));
                    }
                    
                    // making sure there is elements in the list
                    // isEmpty wont work on the list in this case
                    if (responseContent !=null)
                    {
                        
   
                    if (!responseContent.get(0).isEmpty())
                    {
                        for (Map<String, Object> record : responseContent)
                        {
                            Message message = new Message();
                            message.setId(Integer.valueOf(record.get("id").toString()));
                            message.setBody(record.get("body").toString());
                            message.setCreated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(record.get("created_at").toString()));
                            message.setSender_id(Integer.valueOf(record.get("sender_id").toString()));
                            messages.add(message);
                        }
                    }
                    
                    }
                    NetworkManager.getInstance().addToQueue(con);
                                            
                    }
                }
                catch (IOException ex)
                {
                   // Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
            
        }
                return messages;
    }
        public void sendMessage(int sender, int receiver,String body){  
                    ConnectionRequest con = new ConnectionRequest("http://localhost/ScriptsHostAndGuest/addMessage.php?sender="+sender+"&receiver="+receiver+"&body="+body);
                    NetworkManager.getInstance().addToQueueAndWait(con);
                   // NetworkManager.getInstance().addToQueue(con);
                    
    }
        
        public  void sendSms(String mess,String number) throws URISyntaxException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        com.twilio.rest.api.v2010.account.Message message =  com.twilio.rest.api.v2010.account.Message
                .creator(new PhoneNumber(number),  // to
                         new PhoneNumber("+15102401825"),  // from
                         mess)
                .create();
    }
        public String getUsernameById(int user1d){
            ConnectionRequest con = new ConnectionRequest("http://localhost/ScriptsHostAndGuest/getUserById.php?userId="+user1d);
            NetworkManager.getInstance().addToQueueAndWait(con);
             String response = new String (con.getResponseData());
            return response;
        }
}
