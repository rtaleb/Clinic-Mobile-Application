package com.mc.info.lumc;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Doctor extends Person implements Serializable{
    private String specialty;
    private int experienceYears;

    public Doctor() {
        super();
        specialty="";
        experienceYears=0;
    }

    public Doctor(String id, String firstName, String lastName, String phone, String email, Address address, String specialty, int experienceYears) {
        super(firstName, lastName, phone, email, id, "", address, "", "");
        this.specialty = specialty;
        this.experienceYears = experienceYears;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(String experienceYears) {
        this.experienceYears = Integer.parseInt(experienceYears);
    }

    public HashMap<String,String> toHashMap(){
        HashMap<String,String> returnValue = new HashMap<>();
        returnValue.put(DBHandler.COLUMN_FIRST_NAME,getFirstName());
        returnValue.put(DBHandler.COLUMN_LAST_NAME,getLastName());
        returnValue.put(DBHandler.COLUMN_SPECIALTY,getSpecialty());
        returnValue.put(DBHandler.COLUMN_ID,String.valueOf(getId()));
        returnValue.put(DBHandler.COLUMN_PHONE,getPhone());
        returnValue.put(DBHandler.COLUMN_EMAIL,getEmail());
        returnValue.put(DBHandler.COLUMN_EXPERIENCE_YEARS,String.valueOf(getExperienceYears()));
        returnValue.put(DBHandler.COLUMN_BUILDING,getAddress().getBuilding());
        returnValue.put(DBHandler.COLUMN_CITY,getAddress().getCity());
        returnValue.put(DBHandler.COLUMN_STREET,getAddress().getStreet());

        return returnValue;
    }
}
