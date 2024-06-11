package com.mc.info.lumc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Patient extends Person implements Serializable {

    public Patient() {
        super();
    }

    public Patient(String id, String firstName, String lastName, String phone, String email, Address address) {
        super(firstName, lastName, phone, email, id, "", address, "", "");
    }

}
