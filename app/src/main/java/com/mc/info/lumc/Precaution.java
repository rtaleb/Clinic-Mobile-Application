package com.mc.info.lumc;

import java.io.Serializable;

/**
 * Created by hphh4 on 1/6/2017.
 */

public class Precaution implements Serializable {

    private String id;
    private String precName;

    public Precaution() {
        this.id = "";
        this.precName = "";
    }

    public Precaution(String id, String name) {
        this.id = id;
        this.precName = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrecName() {
        return precName;
    }

    public void setName(String name) {
        this.precName = name;
    }
}
