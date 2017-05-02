/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.components.FloatingActionButton;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.ConnectionRequest;

import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import static com.codename1.ui.plaf.Style.BACKGROUND_NONE;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.hostandguest.entities.Property;
import com.hostandguest.entities.User;
import com.hostandguest.services.PropertyService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author BEYRAM-BG
 */
public class addPropertyForm {
    Form add;
    
    public addPropertyForm(Resources theme){
        UIBuilder uib = new UIBuilder();
        add= new Form("H&G - Add Property",BoxLayout.y());
        Container ctn1 = new Container(new BoxLayout((BoxLayout.Y_AXIS)));

        Command cmdBack = new Command("");
        try {
            cmdBack.setIcon(EncodedImage.create("/back.png"));
        } catch (IOException ex) {
            
        }
        add.getToolbar().addCommandToRightBar(cmdBack);
        add.addCommandListener(e -> {
           if(e.getCommand() == cmdBack){
               getMyPropertyForm g = new getMyPropertyForm(theme, User.currentUser);
               g.showMyProperties();
           }
       });
        TextField txtRoomNb = new TextField();txtRoomNb.getStyle().setMarginTop(30);
        txtRoomNb.getStyle().setMarginBottom(20);
        txtRoomNb.setHint("Room Number");
        TextField txtPrice = new TextField();txtPrice.getStyle().setMarginBottom(20);
        txtPrice.setHint("Price");
        TextField txtLocation = new TextField();txtLocation.getStyle().setMarginBottom(20);
        txtLocation.setHint("Location");
        TextArea txtDescription = new TextArea();txtDescription.getStyle().setMarginBottom(20);
        txtDescription.setRows(4);
        txtDescription.setHint("Description");
        CheckBox chkWifi = new CheckBox("WIFI");
        CheckBox chkTV = new CheckBox("TV");
        CheckBox chkKitchen = new CheckBox("Kitchen");
       // Button btUpload = new Button("Upload");
        List<Object> listImg = new ArrayList<>();
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD_A_PHOTO);
        fab.bindFabToContainer(add);
        ActionListener uploadCallBack = e->{
            if (e != null && e.getSource() != null) {
                // Logger.getLogger(addPropertyForm.class.getName()).log(Level.SEVERE, null, ex);
                String filePath = (String)e.getSource();
                System.out.println(filePath);
                listImg.add(filePath);
            }
         };
        fab.addActionListener(f -> {
            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".gif,image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", uploadCallBack);
            } else {
                Display.getInstance().openGallery(uploadCallBack, Display.GALLERY_IMAGE);
            }
        });
        Button btnOk = new Button("Save");
        btnOk.addActionListener(e->{
           Property s = new Property();
           List<Object> listChk = new ArrayList<>();
           s.setImagesPath(listImg);
           s.setDescription(txtDescription.getText());
           s.setLocation(txtLocation.getText());
           s.setPrice(Integer.parseInt(txtPrice.getText()));
           if(chkWifi.isSelected()){
               listChk.add("Wifi");
           }
           if(chkTV.isSelected()){
               listChk.add("TV");
           }
           if(chkKitchen.isSelected()){
               listChk.add("Kitchen");
           }
           s.setHost_id(User.currentUser);
           s.setNbRooms(Integer.parseInt(txtRoomNb.getText()));
           s.setEquipements(listChk);
           PropertyService ps = new PropertyService();
           String nomPic = savePhotoToServer(s.getImagesPath().get(0).toString());
           s.getImagesPath().clear();listImg.clear();
           listImg.add("../../../web/images/uploads/" + nomPic );s.setImagesPath(listImg);
           ps.saveProp(s);
           Dialog.show("Host And Guest","Property Added!", "OK", null);
           getMyPropertyForm gmf = new getMyPropertyForm(theme, User.currentUser);
           gmf.showMyProperties();
        });

        ctn1.add(txtRoomNb);ctn1.add(txtPrice);ctn1.add(txtLocation);ctn1.add(txtDescription);
        ctn1.add(chkWifi);ctn1.add(chkKitchen);ctn1.add(chkTV);ctn1.add(btnOk);
        add.add(ctn1);
    }

    public Form getAddForm() {
        return add;
    }

    public void showAddForm() {
        this.add.show();
    }
    
    public String savePhotoToServer(String filePath){
        MultipartRequest request = new MultipartRequest();
        request.setUrl("http://localhost/ScriptsHostAndGuest/uploadImage.php");
        try {
            request.addData("file", filePath,"image/png");
        } catch (IOException ex) {
         //   Logger.getLogger(addPropertyForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setPriority(ConnectionRequest.PRIORITY_CRITICAL);
        NetworkManager.getInstance().addToQueueAndWait(request);
        return new String(request.getResponseData()) ;      
    }
}
