package com.mc.info.lumc;

import java.io.Serializable;

/**
 * Created by BurgerMan on 12/9/2016.
 */

public class Address implements Serializable{
    private String city,
            street,
            building;

    public Address(){
        city=street=building="";
    }

    public Address(String city, String street, String building) {
        this.city = city;
        this.street = street;
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return building + ", " + street + ", " + city ;
    }
}
