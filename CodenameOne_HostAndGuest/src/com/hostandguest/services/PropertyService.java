/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.services;

import com.Pherialize.Pherialize;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import java.text.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.hostandguest.entities.Property;
import com.hostandguest.interfaces.IProperty;
import com.hostandguest.views.getMyPropertyForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BEYRAM-BG
 */
public class PropertyService implements IProperty {

    public ArrayList<Property> arrayProperties;
    public ArrayList<Property> myArrayProperties;

    public PropertyService() {
        this.arrayProperties = new ArrayList<Property>();
        this.myArrayProperties = new ArrayList<Property>();
    }

    @Override
    public ArrayList<Property> getAllProperty() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/ScriptsHostAndGuest/getAllProperties.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    JSONParser j = new JSONParser();
                    Map<String, Object> listP = j.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    List<Map<String, Object>> list = new ArrayList<>();

                    try {
                        list = (List<Map<String, Object>>) listP.get("Properties");
                    } catch (ClassCastException err) {
                        Log.e(err);
                        list.add((Map<String, Object>) listP.get("Properties"));
                    }

                    for (Map<String, Object> obj : list) {
                        Property e = new Property();
                        e.setId(Integer.parseInt(obj.get("Id").toString()));
                        e.setNbRooms(Integer.parseInt(obj.get("nbRooms").toString()));
                        e.setHost_id(Integer.parseInt(obj.get("hostId").toString()));
                        e.setPrice(Integer.parseInt(obj.get("price").toString()));
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
                            Date dateP = formatter.parse(obj.get("publicationDate").toString());
                            e.setPublicationDate(dateP);
                        } catch (java.text.ParseException ex) {
                            //Logger.getLogger(PropertyService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        e.setLocation(obj.get("location").toString());
                        e.setDescription(obj.get("description").toString());
                        e.setImagesPath(new ArrayList(Pherialize.unserialize(obj.get("imagesPath").toString()).toArray().values()));
                        e.setEquipements(new ArrayList(Pherialize.unserialize(obj.get("equipements").toString()).toArray().values()));
                        arrayProperties.add(e);
                    }
                } catch (IOException ex) {
                    Log.e(ex);
                } catch (NullPointerException ex) {
                    Log.e(ex);
                    Dialog.show("Notice", "No Properties Were Found", "OK", null);
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        NetworkManager.getInstance().addErrorListener(e -> {
            e.consume();
            Dialog.show("Error", "Connection Error, Please Try Again Later", "OK", null);
        });
        return arrayProperties;
    }

    @Override
    public ArrayList<Property> getMyProperty(int currentUser) {
        myArrayProperties.clear();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/ScriptsHostAndGuest/getMyProperties.php?hostId=" + currentUser);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    JSONParser j = new JSONParser();
                    Map<String, Object> listP = j.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    List<Map<String, Object>> list = new ArrayList<>();

                    try {
                        list = (List<Map<String, Object>>) listP.get("Properties");
                    } catch (ClassCastException err) {
                        Log.e(err);
                        list.add((Map<String, Object>) listP.get("Properties"));
                    }

                    for (Map<String, Object> obj : list) {
                        Property e = new Property();
                        e.setId(Integer.parseInt(obj.get("Id").toString()));
                        e.setNbRooms(Integer.parseInt(obj.get("nbRooms").toString()));
                        e.setHost_id(Integer.parseInt(obj.get("hostId").toString()));
                        e.setPrice(Integer.parseInt(obj.get("price").toString()));
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
                            Date dateP = formatter.parse(obj.get("publicationDate").toString());
                            e.setPublicationDate(dateP);
                        } catch (java.text.ParseException ex) {
                            //Logger.getLogger(PropertyService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        e.setLocation(obj.get("location").toString());
                        e.setDescription(obj.get("description").toString());
                        e.setImagesPath(new ArrayList(Pherialize.unserialize(obj.get("imagesPath").toString()).toArray().values()));
                        e.setEquipements(new ArrayList(Pherialize.unserialize(obj.get("equipements").toString()).toArray().values()));
                        myArrayProperties.add(e);
                    }
                } catch (IOException | NullPointerException ex) {
                    Log.e(ex);
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        NetworkManager.getInstance().addErrorListener(e -> {
            e.consume();
            Dialog.show("Error", "Connection Error, Please Try Again Later", "OK", null);
        });
        return myArrayProperties;
    }

    @Override
    public void deleteProperty(int propertyId) {
        ConnectionRequest request = new ConnectionRequest("http://localhost/ScriptsHostAndGuest/deleteProperty.php?PropId=" + propertyId);
        NetworkManager.getInstance().addToQueueAndWait(request);
        Dialog.show("Host And Guest", new String(request.getResponseData()), "OK", null);

    }

    public void saveProp(Property s) {
        String urlCheck = "";
        String urlIMG = "";
        for (Object o : s.getEquipements()) {
            urlCheck += "&check[]=" + o.toString();
        }
        System.out.println("size" + s.getImagesPath().size());
        for (Object x : s.getImagesPath()) {
            System.out.println("");
            urlIMG += "&img[]=" + x.toString();
        }
        String url = "http://localhost/ScriptsHostAndGuest/addProperty.php?hostId=" + s.getHost_id() + "&price="
                + s.getPrice() + "&description=" + s.getDescription() + urlIMG
                + "&location=" + s.getLocation() + "&nbroom=" + s.getNbRooms() + urlCheck;
        System.out.println(url);
        ConnectionRequest request = new ConnectionRequest(url);
        NetworkManager.getInstance().addToQueueAndWait(request);
    }

    public void updateProp(Property s) {
        String urlCheck = "";
        for (Object o : s.getEquipements()) {
            urlCheck += "&check[]=" + o.toString();
        }

        String url = "http://localhost/ScriptsHostAndGuest/updateProperty.php?propId=" + s.getId() + "&price="
                + s.getPrice() + "&description=" + s.getDescription()
                + "&location=" + s.getLocation() + "&nbroom=" + s.getNbRooms() + urlCheck;
        System.out.println(url);
        ConnectionRequest request = new ConnectionRequest(url);
        NetworkManager.getInstance().addToQueueAndWait(request);
        System.out.println(new String(request.getResponseData()));
    }

}
