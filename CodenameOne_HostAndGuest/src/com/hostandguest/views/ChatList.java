/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.hostandguest.entities.Message;
import com.hostandguest.entities.User;
import com.hostandguest.services.MessageService;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// Install the Java helper library from twilio.com/docs/java/install

/**
 *
 * @author Marouane
 */
public class ChatList {

    private Form listForm;
    private Form conversationForm;
    private Container ctn1;
    private Resources theme;
    private MessageService ms;
     String number="";
    public ChatList() {
       

            ms = new MessageService();
            ArrayList<User> users = ms.getUserList();
        
        listForm =new Form("Mes Discussions",new BoxLayout(BoxLayout.Y_AXIS));
        
        
        
        //listForm.getToolbar().addCommandToRightBar(cmdAdd);
        Style st = new Style();
         FontImage img2 = FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE,st );

        for (User s : users) {
            Container ctn1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Label lblName = new Label(s.getUsername(),img2);
          //  lblName.getUnselectedStyle().setAlignment(Label.CENTER);
            lblName.getUnselectedStyle().setBorder(Border.createGrooveBorder(2));
            lblName.getUnselectedStyle().setPaddingLeft(18);
            lblName.addPointerPressedListener(e->{
                Message.currentUserConversation=s.getId();
                displayConversation(s);
            });
            ctn1.add(lblName);

            ctn1.setLeadComponent(lblName);
            listForm.add(ctn1);
        }

        listForm.show();

    }
            public Container insertMessages(){
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
                
                Container ctn = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                ArrayList<Message> messages = ms.getConversation(User.currentUser, Message.currentUserConversation);
                        Style s = new Style();
                FontImage img = FontImage.createMaterial(FontImage.MATERIAL_FACE,s );
                FontImage img2 = FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE,s );
                    for (Message message : messages) {
                       Label date = new Label(dateFormat.format(message.getCreated_at()));
                        date.setAlignment(Label.CENTER);
                        date.getUnselectedStyle().setFont(smallPlainSystemFont);
            Label mess ;
            if(message.getSender_id() == User.currentUser){
                mess = new Label(message.getBody(),img);
                mess.setTextPosition(Component.LEFT);
                mess.setAlignment(Label.RIGHT);
            }else{
                mess = new Label(message.getBody(),img2);
                mess.setAlignment(Label.LEFT);
                mess.getUnselectedStyle().setFgColor(94145);
                mess.repaint();
            }
            ctn.add(date);
            ctn.add(mess);
        }
                    return ctn;
        }
    
    public void displayConversation(User user){
                                    if(Message.currentUserConversation==1){
                number="+21655002851";
            }
         ctn1 = insertMessages();

        conversationForm = new Form("Discussion avec "+user.getUsername(),new BoxLayout(BoxLayout.Y_AXIS));;
        Command back = new Command("retour");

        conversationForm.getToolbar().addCommandToRightBar("Back", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
         listForm.showBack();
            }
        });
        
        TextField messageInput = new TextField();
        messageInput.setHint("write your message");
        messageInput.setColumns(10);
        Button send = new Button("envoyer");
        CheckBox rb = new CheckBox("SMS");
        send.addActionListener(e->{
            if(!"".equals(messageInput.getText())){
                try {
                    if(!"".equals(number) && rb.isSelected())ms.sendSms(ms.getUsernameById(User.currentUser)+" vous a dit : "+messageInput.getText(), number);
                    Container cn;
                    ms.sendMessage(User.currentUser, Message.currentUserConversation, messageInput.getText());
                    messageInput.setText("");
                    cn=insertMessages();
                    conversationForm.replace(ctn1,cn , null);
                    ctn1=cn;
                    conversationForm.refreshTheme();
                    conversationForm.scrollComponentToVisible(send);
                } catch (URISyntaxException ex) {
                }
            }
    });
        Container ctn2 = new Container(new FlowLayout());
        ctn2.add(messageInput);
        ctn2.add(send);
        ctn2.add(rb);
        
        conversationForm.add(ctn1);
        conversationForm.add(ctn2);
        conversationForm.show();
        conversationForm.scrollComponentToVisible(send);
        Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {
    @Override
    public void run() {
        refresh();
    }
};

timer.schedule(myTask, 2000, 2000);
    }
    
    public void refresh(){
            Container cn;
            cn=insertMessages();
            conversationForm.replace(ctn1,cn , null);
            ctn1=cn;
            conversationForm.refreshTheme();
            /*
                LocalNotification n = new LocalNotification();
n.setId("demo-notification");
n.setAlertBody("It's time to take a break and look at me");
n.setAlertTitle("Break Time!");
n.setAlertSound("beep-01a.mp3");

        Display.getInstance().scheduleLocalNotification(
        n,
        System.currentTimeMillis() + 10 * 1000, // fire date/time
        LocalNotification.REPEAT_MINUTE  // Whether to repeat and what frequency

);
*/    }

    
}
