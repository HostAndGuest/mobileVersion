/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.googlemaps.MapContainer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.hostandguest.entities.Property;
import com.hostandguest.entities.User;
import com.hostandguest.services.PropertyService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author BEYRAM-BG
 */
public class GoogleMapForm {

    
    private static final String HTML_API_KEY = "AIzaSyA9UxH5E0hlB9792LUtZkBNKmR2m-Wcdbw";
    private Form currentMap;
    private ArrayList<Property> arrayPlaces = new ArrayList<Property>();
    
    public GoogleMapForm(Resources theme){
        UIBuilder ui = new UIBuilder();
        currentMap= new Form("H&G - Google Maps");
        currentMap.setLayout(new BorderLayout());
        MapContainer cnt = new MapContainer(HTML_API_KEY);
        cnt.setCameraPosition(new Coord(36.840,10.1385));
        PropertyService ps = new PropertyService();
        arrayPlaces = ps.getAllProperty();
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(new Float(0.3)));
        EncodedImage im = EncodedImage.createFromImage(markerImg, false);
        for(Property e : arrayPlaces){
             Coord place = getCoords(e.getLocation());
            cnt.addMarker(
                    im,
                    place,
                    "Property Location:" + e.getLocation(),
                    "Property Room Nb:" + e.getNbRooms(),
                     evt -> {
                            Dialog.show("Host And Guest" + "Property Id" + e.getId(),"Location :" + e.getLocation(), "OK", null);
                     }
            );
        }
        Container root = LayeredLayout.encloseIn(
                BorderLayout.center(cnt)
        );
        new CommonMenu(theme, currentMap);
        currentMap.add(BorderLayout.CENTER, root);

    }

    private Form getGoogleMapForm() {
        return currentMap;
    }
    
    public void showGoogleMap(){
        currentMap.show();
    }
    
    public Coord getCoords(String address) {
        Coord ret = null;
        try {
            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/geocode/json", false);
            request.addArgument("key", HTML_API_KEY);
            request.addArgument("address", address);

            NetworkManager.getInstance().addToQueueAndWait(request);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
            if (response.get("results") != null) {
                ArrayList results = (ArrayList) response.get("results");
                if (results.size() > 0) {
                    LinkedHashMap location = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) results.get(0)).get("geometry")).get("location");
                    ret = new Coord((double) location.get("lat"), (double) location.get("lng"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    private void prepareMenu(Resources theme){
        Command cmdMy = new Command("H&G - My Properties");
        Command cmdAll = new Command("H&G - All Properties");
        Command cmdGmap = new Command("H&G - Map");
        currentMap.getToolbar().addCommandToOverflowMenu(cmdMy);
        currentMap.getToolbar().addCommandToOverflowMenu(cmdAll);
        currentMap.getToolbar().addCommandToOverflowMenu(cmdGmap);
        currentMap.addCommandListener(e -> {
           if(e.getCommand() == cmdAll){
               getAllPropertyForm propAll = new getAllPropertyForm(theme);
                propAll.showAllProperties();
           } 
           if(e.getCommand() == cmdMy){
               getMyPropertyForm MyPropForm = new getMyPropertyForm(theme,User.currentUser);
               MyPropForm.showMyProperties();
           }
           if(e.getCommand() == cmdGmap){  
           }
       });
    }
    
    
    
    
}
