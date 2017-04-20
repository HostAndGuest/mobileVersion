/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.entities;

/**
 *
 * @author The
 */
public class Experience {
    private int id;
    private String description;
    
    // One To One
    private TypeExperience typeExperience;
    
    // Many To One
    private Property property;

    public Experience() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeExperience getTypeExperience() {
        return typeExperience;
    }

    public void setTypeExperience(TypeExperience typeExperience) {
        this.typeExperience = typeExperience;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "Experience{" + "id=" + id + ", description=" + description + ", typeExperience=" + typeExperience + ", property=" + property + '}';
    }
}
