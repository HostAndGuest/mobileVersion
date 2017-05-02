/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.codename1.util.StringUtil;
import com.hostandguest.entities.Message;
import com.hostandguest.entities.Property;
import com.hostandguest.entities.User;
import com.hostandguest.services.MessageService;
import com.hostandguest.services.PropertyService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author BEYRAM-BG
 */
public class getAllPropertyForm {
    Form fromListProp ;
    ArrayList<Property> arrayProperties = new ArrayList<Property>();
    EncodedImage encoded ;
    ArrayList<Property> seatchedProperties = new ArrayList<Property>();
    
    public getAllPropertyForm(Resources theme){
        UIBuilder ui = new UIBuilder();
        fromListProp = new Form("H&G - List Properties",BoxLayout.y());
        try {
            encoded = EncodedImage.create("/loading.png");
            } catch (IOException ex) {
            }
        prepareMenu(theme);
        arrayProperties = new PropertyService().getAllProperty();
        cellForRow(theme);
    }

    public Form getAllPropertiesForm() {
        return fromListProp;
    }
    
    private void cellForRow(Resources theme){
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_SEARCH);
        fab.addActionListener(e->{
            Label lab = new Label("Set your Location :");
            TextField myTF = new TextField();
            myTF.setHint("Set your Location");
            Dialog abc = new Dialog();abc.add(lab);
            Button srch = new Button("Search");
            srch.addActionListener(l -> {
                String loc = myTF.getText();
                for(Property p : arrayProperties){
                    if(Util.split(p.getLocation().toLowerCase(), loc.toLowerCase()).length >=2){
                        seatchedProperties.add(p);
                    }
                }
                System.out.println("Searched List Size :" + seatchedProperties.size());
                arrayProperties = seatchedProperties;
                fromListProp = new Form("H&G - Search Properties",BoxLayout.y());
                try {
                    encoded = EncodedImage.create("/loading.png");
                    } catch (IOException ex) {
                    }
                prepareMenu(theme);
                for(Property s : arrayProperties){
                    fromListProp.add(displayCell(s));
                }
                FloatingActionButton reloadFab = FloatingActionButton.createFAB(FontImage.MATERIAL_LIST);
                reloadFab.bindFabToContainer(fromListProp);
                reloadFab.addActionListener(j->{
                    fromListProp = new Form("H&G - List Properties",BoxLayout.y());
                    try {
                    encoded = EncodedImage.create("/loading.png");
                    } catch (IOException ex) {
                    }
                    
                    prepareMenu(theme);
                    arrayProperties = new PropertyService().getAllProperty();
                    cellForRow(theme);
                    fromListProp.showBack();
                });
                fromListProp.show();
            });
            abc.add(myTF);abc.add(srch);
            abc.show();//show dialog.
        });
        fab.bindFabToContainer(fromListProp);
        for(Property s : arrayProperties){
            fromListProp.add(displayCell(s));
        }
    }
    
    private Container displayCell(Property s){
        
        Container ctn1 = new Container(new BoxLayout((BoxLayout.Y_AXIS)));
        Container ctn2 = new Container(new BoxLayout((BoxLayout.X_AXIS)));
        Container ctn3 = new Container(new BoxLayout((BoxLayout.Y_AXIS)));
        Container ctn4 = new Container(new BoxLayout((BoxLayout.Y_AXIS)));
        Label labNom = new Label(" Property Id: " + s.getId());
        labNom.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        ctn3.add(labNom);
        String loc = s.getLocation() ;
       /* if(loc.length()>6){
            loc = loc.substring(0, Math.min(6, s.getLocation().length())) +"...";
        }*/
        Label labLocation = new Label(" Location :  " + loc);
        Label labPrice = new Label(" Price :  " + s.getPrice() + " TND");
        labPrice.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        labLocation.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        ctn3.add(labLocation);
        ctn3.add(labPrice);
        Button deleteButton = new Button("Delete");
        deleteButton.setName("del"+ s.getId());
        deleteButton.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        deleteButton.getStyle().setFgColor(0xFF0033, true);
        Button UpButton = new Button("Update");
        UpButton.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        UpButton.getStyle().setFgColor(0x009999, true);
        deleteButton.addActionListener(e->{
            int idProp = Integer.parseInt( StringUtil.replaceAll(((Button) e.getSource()).getName(),"del",""));
            new PropertyService().deleteProperty(idProp);         
            System.out.println("Property : " + idProp);
            ctn1.remove();  
        });
        ctn2.add(ctn3);
        ctn2.add(ctn4);
        String firstImg = s.getImagesPath().get(0).toString();
        Image imgServer = URLImage.createToStorage(encoded, "IMG"+firstImg, "http://localhost/PHPstormProjects/Host_n_Guest/web/images/uploads/" + firstImg );
        ImageViewer img = new ImageViewer(imgServer);
        img.addPointerReleasedListener(e ->{
            System.out.println("click");
            displayDetail(s);
        });
        Dimension dCtn1 = new Dimension();
        dCtn1.setHeight(500);
        img.setPreferredSize(dCtn1);
        img.setImageInitialPosition(ImageViewer.IMAGE_FILL);
        ctn1.add(img);
        ctn1.getStyle().setBorder(Border.createLineBorder(2));     
        ctn1.add(ctn2);
        return ctn1;
    }
    
    public void showAllProperties(){
        fromListProp.show();
    }
    
    private void prepareMenu(Resources theme){
        Command cmdMy = new Command("My Properties");
        Command cmdAll = new Command("All Properties");
        Command cmdGmap = new Command("H&G - Map");
        fromListProp.getToolbar().addCommandToOverflowMenu(cmdMy);
        fromListProp.getToolbar().addCommandToOverflowMenu(cmdAll);
        fromListProp.getToolbar().addCommandToOverflowMenu(cmdGmap);
        fromListProp.addCommandListener(e -> {
           if(e.getCommand() == cmdAll){
           } 
           if(e.getCommand() == cmdMy){
               getMyPropertyForm MyPropForm = new getMyPropertyForm(theme,User.currentUser);
               MyPropForm.showMyProperties();
           }
           if(e.getCommand() == cmdGmap){
               GoogleMapForm Gform = new GoogleMapForm(theme);
               Gform.showGoogleMap();
           }
       });
    }
    
    private void displayDetail(Property s) {
        Container ctn1 = new Container(new BoxLayout((BoxLayout.Y_AXIS)));
        Container ctnImgs = new Container(new BoxLayout((BoxLayout.X_AXIS)));
        ctnImgs.setScrollableX(true);
        Label lb1 = new Label(" Property Information :");
        Label lb2 = new Label(" Pictures :");
        Form detail = new Form("H&G - Property Detail ");
        Command cmdBack = new Command("");
        try {
            cmdBack.setIcon(EncodedImage.create("/back.png"));
        } catch (IOException ex) {
            
        }
       // detail.addCommand(cmdBack);
        detail.getToolbar().addCommandToRightBar(cmdBack);
        detail.addCommandListener(e -> {
           if(e.getCommand() == cmdBack){
               fromListProp.showBack();
           }
       });
        for(Object o : s.getImagesPath()){
            String firstImg = o.toString();
            Image imgServer = URLImage.createToStorage(encoded, "img"+firstImg, "http://localhost/PHPstormProjects/Host_n_Guest/web/images/uploads/" + firstImg );
            ImageViewer img = new ImageViewer(imgServer);
            Dimension dCtn1 = new Dimension();
            dCtn1.setHeight(500);
            dCtn1.setWidth(700);
            img.setPreferredSize(dCtn1);
            img.setImageInitialPosition(ImageViewer.IMAGE_FILL);
            img.getStyle().setMarginLeft(20);
            ctnImgs.add(img); 
        }
        Container Desc = new Container(new BoxLayout((BoxLayout.X_AXIS)));
        Label labLocation = new Label(" Location :  " + s.getLocation());
        Label labPrice = new Label(" Price :  " + s.getPrice() + " TND");
        Label labNom = new Label(" Publication date : " + new SimpleDateFormat("dd/MM/yyyy").format(s.getPublicationDate()));   
        Label labHost = new Label(" Host Id : " + s.getHost_id()); 
        SpanLabel labDesc = new SpanLabel(" Description : " + s.getDescription()); 
        Label lb = new Label(" Description : ");
        Desc.add(lb);Desc.add(labDesc);
        labDesc.setScrollVisible(true);
        Label labEquip = new Label(" Equipement : " + s.getEquipements().toString()); 
        Label labRnb = new Label(" Room number : " + s.getNbRooms()); 
        labLocation.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        labNom.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        labPrice.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        labHost.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        lb.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        labEquip.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        labRnb.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        lb1.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        lb2.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        lb1.getStyle().setMarginTop(40);
        lb1.getStyle().setMarginBottom(40);
        ctnImgs.getStyle().setMarginBottom(30);
        lb1.getStyle().setFgColor(0xCC0000, true);
        lb2.getStyle().setFgColor(0xCC0000, true);
        ctn1.add(lb2);
        ctn1.add(ctnImgs);
        Container ctnButt = new Container(new BoxLayout((BoxLayout.X_AXIS)));
        ctnButt.getStyle().setMarginLeft(20);
        Label labC = new Label("Contact");labC.getStyle().setMarginBottom(10);
        labC.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        FloatingActionButton chatButton = FloatingActionButton.createFAB(FontImage.MATERIAL_CHAT);
        ctnButt.add(chatButton);ctnButt.add(labC);
        Label labR = new Label("Book");labR.getStyle().setMarginBottom(10);
        labR.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        FloatingActionButton bookButton = FloatingActionButton.createFAB(FontImage.MATERIAL_SAVE);
        ctnButt.add(bookButton);ctnButt.add(labR);
        ctn1.add(ctnButt);
        ctn1.add(lb1);
        ctn1.add(labLocation);ctn1.add(labPrice);ctn1.add(labNom);ctn1.add(labHost);
        ctn1.add(Desc);ctn1.add(labEquip);ctn1.add(labRnb);

        chatButton.addActionListener(f -> {
            MessageService ms = new MessageService();
            int host_id = s.getHost_id();
            Message.currentUserConversation = host_id;
            System.out.println("Host_Id " + Message.currentUserConversation);
            ChatList cl = new ChatList();
            System.out.println(ms.getUsernameById(host_id));
            cl.displayConversation(ms.getUsernameById(host_id));
        });
        
        bookButton.addActionListener(f -> {
            int idProp = s.getId();
            System.out.println("Property Id " + idProp);
        });
        
        detail.add(ctn1);
        detail.getStyle().setAlignment(Component.CENTER);
        detail.show();
    }
        
    
}
