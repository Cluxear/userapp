package com.tw.userapp.service.models;

import com.tw.userapp.domain.Address;


public class InfoPerso {

    private String name;
    private String firstName;
    private String lastName;
    private String[] phone;
    private String[] email;
    private Address address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        if(name != null && name.indexOf(' ') > -1)
            return name.split("\\s", '2')[0];
        else
            return "";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if(name != null && name.indexOf(' ') > -1)
            return name.split("\\s", '2')[1];
        else
            return "";
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone[0];
    }

    public void setPhone(String[] phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email[0];
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Personnel Info{" +
            "name='" + name + '\'' +
            ", firstName='" + this.getFirstName() + '\'' +
            ", lastName='" + this.getLastName() + '\'' +
            ", phone='" + this.getPhone() + '\'' +
            ", email='" + this.getEmail() + '\'' +
            ", address=" + address +
            '}';
    }
}
