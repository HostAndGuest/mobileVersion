/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.interfaces;

import com.hostandguest.entities.Property;
import java.util.ArrayList;

/**
 * 
 */
public interface IProperty {
    public ArrayList<Property> getAllProperty();
    public ArrayList<Property> getMyProperty(int currentUser);
    public void deleteProperty(int propertyId);
}
