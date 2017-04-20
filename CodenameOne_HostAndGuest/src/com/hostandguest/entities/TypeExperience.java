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
public class TypeExperience {
    private int id;
    private String name;

    public TypeExperience() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TypeExperience{" + "id=" + id + ", name=" + name + '}';
    }
}
