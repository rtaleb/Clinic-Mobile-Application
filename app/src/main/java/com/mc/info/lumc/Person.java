package com.mc.info.lumc;

import java.io.Serializable;

/**
 * Created by BurgerMan on 12/9/2016.
 */

public class Person  implements Serializable{
    private String firstName,
            lastName,
            phone,
            email,
            id,
            bloodGroup,
            weight,
            height;
    private Address address;


    public Person() {
        this.firstName = "";
        this.lastName = "";
        this.phone = "";
        this.email = "";
        this.id = "";
        this.bloodGroup = "";
        this.address = new Address();
        this.weight = "";
        this.height = "";
    }

    public Person(String firstName, String lastName, String phone, String email, String id, String bloodGroup, Address address, String weight, String height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.id = id;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.weight = weight;
        this.height = height;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        return id != null ? id.equals(person.id) : person.id == null;

    }
}
