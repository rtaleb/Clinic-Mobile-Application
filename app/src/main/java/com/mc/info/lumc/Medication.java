package com.mc.info.lumc;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by hphh4 on 1/6/2017.
 */

public class Medication implements Serializable {

    private String id;
    private String Medname,
                   from,
                   to;


    public Medication() {
        this.id = "";
        this.Medname = "";
        this.from = "";
        this.to = "";

    }

    public Medication(String id, String Medname, String from, String to) {
        this.id = id;
        this.Medname = Medname;
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedname() {
        return Medname;
    }

    public void setMedname(String medname) {
        this.Medname = medname;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public HashMap<String,String> toHashMap() {
        //TODO
        return null;
    }
}
