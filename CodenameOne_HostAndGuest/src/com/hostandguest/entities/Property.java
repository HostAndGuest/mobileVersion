/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author The
 */
public class Property {
    private int id;
    private int nbRooms;
    private int price;
    private Date publicationDate;
    private String description;
    private List<Object> equipements;
    private List<Object> imagesPath;
    private String location;
    private boolean reported = false;
    private boolean enabled = true;
    
    // Many To One Relationship
    private int host_id;

    public Property() {
        this.publicationDate = new Date();
        equipements = new ArrayList<>();
        imagesPath = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbRooms() {
        return nbRooms;
    }

     public List<Object> getEquipements() {
        return equipements;
    }

    public void setEquipements(List<Object> equipements) {
        this.equipements = equipements;
    }
    
    public void setNbRooms(int nbRooms) {
        this.nbRooms = nbRooms;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Object> getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(List<Object> imagesPath) {
        this.imagesPath = imagesPath;
    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getHost_id() {
        return host_id;
    }

    public void setHost_id(int host_id) {
        this.host_id = host_id;
    }

    @Override
    public String toString() {
        return "Property{" + "id=" + id + ", nbRooms=" + nbRooms + ", price=" + price + ", publicationDate=" + publicationDate + ", description=" + description + ", equipements=" + equipements + ", imagesPath=" + imagesPath + ", location=" + location + ", reported=" + reported + ", enabled=" + enabled + ", host_id=" + host_id + '}';
    }
    
}