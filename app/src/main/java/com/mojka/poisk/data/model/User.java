package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private Integer id;

    @SerializedName("phone")
    private String phone;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("city")
    private String city;

    @SerializedName("car")
    private String car;

    @SerializedName("token")
    private String token;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", car='" + car + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public User() {
    }

    public User(Integer id, String phone, String name, String city, String car, String token) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.city = city;
        this.car = car;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
