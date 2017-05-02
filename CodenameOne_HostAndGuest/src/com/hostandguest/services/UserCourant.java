/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.services;

import com.hostandguest.entities.User;

/**
 *
 * @author Asus
 */
public class UserCourant {
    private static User currentuser ;

    /**
     * @return the currentuser
     */
    public static User getCurrentuser() {
        return currentuser;
    }

    /**
     * @param aCurrentuser the currentuser to set
     */
    public static void setCurrentuser(User aCurrentuser) {
        currentuser = aCurrentuser;
    }
    
    
    
}
