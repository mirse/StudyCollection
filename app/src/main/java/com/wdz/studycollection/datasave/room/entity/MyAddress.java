package com.wdz.studycollection.datasave.room.entity;

public class MyAddress {
    public String city;
    public String street;


    public MyAddress(String city, String street) {
        this.city = city;
        this.street = street;
    }

    @Override
    public String toString() {
        return "MyAddress{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
