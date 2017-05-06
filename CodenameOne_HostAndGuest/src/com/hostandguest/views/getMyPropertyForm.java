/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
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
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.codename1.util.StringUtil;
import com.hostandguest.entities.Property;
import com.hostandguest.entities.User;
import com.hostandguest.services.PropertyService;
import com.hostandguest.services.UserCourant;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEYRAM-BG
 */
public class getMyPropertyForm {

    Form formMyListProp;
    ArrayList<Property> myArrayProperties = new ArrayList<Property>();
    EncodedImage encoded;
    int userId;

    public getMyPropertyForm(Resources theme, int userId) {

        UIBuilder ui = new UIBuilder();
        try {
            encoded = EncodedImage.create("/loading.png");
        } catch (IOException ex) {
        }
        formMyListProp = new Form("H&G - My List Properties", BoxLayout.y());
        new CommonMenu(theme, formMyListProp);

        this.userId = userId;
        myArrayProperties.clear();
        myArrayProperties = new PropertyService().getMyProperty(userId);

        cellForRow(theme);
    }

    public Form getFormMyListProp() {
        return formMyListProp;
    }

    public void showMyProperties() {
        this.formMyListProp.show();
    }

    private void cellForRow(Resources theme) {
        // Container AddCtn = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_SCALE));
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.bindFabToContainer(formMyListProp);
        fab.addActionListener(e -> {
            addPropertyForm addP = new addPropertyForm(theme);
            addP.showAddForm();
        });
        for (Property s : myArrayProperties) {
            formMyListProp.add(displayCell(s, theme));
        }
    }

    private Container displayCell(Property s, Resources theme) {
        Container ctn1 = new Container(new BoxLayout((BoxLayout.Y_AXIS)));
        Container ctn2 = new Container(new BoxLayout((BoxLayout.X_AXIS)));
        Container ctn3 = new Container(new BoxLayout((BoxLayout.Y_AXIS)));
        Container ctn4 = new Container(new BoxLayout((BoxLayout.Y_AXIS)));
        Container ctn5 = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_SCALE));
        ctn5.getStyle().setMarginLeft(100);
        Label labNom = new Label(" Property Id: " + s.getId());
        labNom.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        ctn3.add(labNom);
        String loc = s.getLocation();
        if (loc.length() > 6) {
            loc = loc.substring(0, Math.min(6, s.getLocation().length())) + "...";
        }
        Label labLocation = new Label(" Location :  " + loc);
        Label labPrice = new Label(" Price :  " + s.getPrice() + " TND");
        labPrice.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        labLocation.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        ctn3.add(labLocation);
        ctn3.add(labPrice);
        Button deleteButton = new Button("Delete");
        deleteButton.setName("del" + s.getId());
        deleteButton.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        deleteButton.getStyle().setFgColor(0xFF0033, true);
        Button UpButton = new Button("Update");
        UpButton.setName("up" + s.getId());
        UpButton.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        UpButton.getStyle().setFgColor(0x009999, true);
        UpButton.addActionListener(e -> {
            System.out.println("upp");
            int idProp = Integer.parseInt(StringUtil.replaceAll(((Button) e.getSource()).getName(), "up", ""));
            Property nP = new Property();
            for (Property p : myArrayProperties) {
                if (p.getId() == idProp) {
                    nP = p;
                }
            }
            updateProp(nP, theme);
        });
        deleteButton.addActionListener(e -> {
            int idProp = Integer.parseInt(StringUtil.replaceAll(((Button) e.getSource()).getName(), "del", ""));
            new PropertyService().deleteProperty(idProp);
            System.out.println("Property : " + idProp);
            ctn1.remove();
        });
        ctn5.add(BorderLayout.SOUTH, deleteButton);
        ctn5.add(BorderLayout.NORTH, UpButton);
        ctn4.add(ctn5);
        ctn2.add(ctn3);
        ctn2.add(ctn4);
        String firstImg = s.getImagesPath().get(0).toString();
        Image imgServer = URLImage.createToStorage(encoded, "IMG" + firstImg, "http://localhost/PHPstormProjects/Host_n_Guest/web/images/uploads/" + firstImg);
        ImageViewer img = new ImageViewer(imgServer);
        img.addPointerReleasedListener(e -> {
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
        // ctn1.getStyle().setPadding(0, 0, 5, 5);
        ctn1.getStyle().setMarginBottom(30);
        ctn1.getStyle().setMarginTop(30);

        return ctn1;
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
            if (e.getCommand() == cmdBack) {
                formMyListProp.showBack();
            }
        });
        for (Object o : s.getImagesPath()) {
            String firstImg = o.toString();
            Image imgServer = URLImage.createToStorage(encoded, "img" + firstImg, "http://localhost/PHPstormProjects/Host_n_Guest/web/images/uploads/" + firstImg);
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
        Desc.add(lb);
        Desc.add(labDesc);
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
        ctn1.add(lb1);
        ctn1.add(labLocation);
        ctn1.add(labPrice);
        ctn1.add(labNom);
        ctn1.add(labHost);
        ctn1.add(Desc);
        ctn1.add(labEquip);
        ctn1.add(labRnb);
        detail.add(ctn1);
        detail.getStyle().setAlignment(Component.CENTER);
        detail.show();
    }

    public void updateProp(Property p, Resources theme) {
        System.out.println(p.getNbRooms() + " " + p.getPrice());
        UIBuilder uib = new UIBuilder();
        Form updateForm = new Form("H&G - Update Property", BoxLayout.y());
        Container ctn1 = new Container(new BoxLayout((BoxLayout.Y_AXIS)));
        Command cmdBack = new Command("");
        try {
            cmdBack.setIcon(EncodedImage.create("/back.png"));
        } catch (IOException ex) {

        }
        updateForm.getToolbar().addCommandToRightBar(cmdBack);
        updateForm.addCommandListener(e -> {
            if (e.getCommand() == cmdBack) {
                getMyPropertyForm g = new getMyPropertyForm(theme, User.currentUser);
                g.showMyProperties();
            }
        });
        TextField txtRoomNb = new TextField(p.getNbRooms() + "");
        txtRoomNb.getStyle().setMarginTop(30);
        txtRoomNb.getStyle().setMarginBottom(20);
        txtRoomNb.setHint("Room Number");
        TextField txtPrice = new TextField(p.getPrice() + "");
        txtPrice.getStyle().setMarginBottom(20);
        txtPrice.setHint("Price");
        TextField txtLocation = new TextField(p.getLocation());
        txtLocation.getStyle().setMarginBottom(20);
        txtLocation.setHint("Location");
        TextArea txtDescription = new TextArea(p.getDescription());
        txtDescription.getStyle().setMarginBottom(20);
        txtDescription.setRows(4);
        txtDescription.setHint("Description");
        CheckBox chkWifi = new CheckBox("WIFI");
        CheckBox chkTV = new CheckBox("TV");
        CheckBox chkKitchen = new CheckBox("Kitchen");
        for (Object o : p.getEquipements()) {
            if (o.toString().equals("WIFI")) {
                chkWifi.setSelected(true);
            }
            if (o.toString().equals("TV")) {
                chkTV.setSelected(true);
            }
            if (o.toString().equals("Kitchen")) {
                chkKitchen.setSelected(true);
            }
        }
        /* if(p.getEquipements().contains((Object)"Wifi")){
            chkWifi.setSelected(true);
        }
        if(p.getEquipements().contains((Object)"Kitchen")){
           chkWifi.setSelected(true);
        }
        if(p.getEquipements().contains((Object)"TV")){
            chkTV.setSelected(true);
        }*/
        Button btnOk = new Button("Update");
        btnOk.addActionListener(e -> {
            Property s = new Property();
            s.setId(p.getId());
            List<Object> listChk = new ArrayList<>();
            s.setDescription(txtDescription.getText());
            s.setLocation(txtLocation.getText());
            s.setPrice(Integer.parseInt(txtPrice.getText()));
            if (chkWifi.isSelected()) {
                listChk.add("WIFI");
            }
            if (chkTV.isSelected()) {
                listChk.add("TV");
            }
            if (chkKitchen.isSelected()) {
                listChk.add("Kitchen");
            }
            s.setHost_id(User.currentUser);
            s.setNbRooms(Integer.parseInt(txtRoomNb.getText()));
            s.setEquipements(listChk);
            PropertyService ps = new PropertyService();
            ps.updateProp(s);
            Dialog.show("Host And Guest", "Property Updated!", "OK", null);
            getMyPropertyForm gmf = new getMyPropertyForm(theme, User.currentUser);
            gmf.showMyProperties();
        });

        ctn1.add(txtRoomNb);
        ctn1.add(txtPrice);
        ctn1.add(txtLocation);
        ctn1.add(txtDescription);
        ctn1.add(chkWifi);
        ctn1.add(chkKitchen);
        ctn1.add(chkTV);
        ctn1.add(btnOk);
        updateForm.add(ctn1);
        updateForm.show();
    }

}
