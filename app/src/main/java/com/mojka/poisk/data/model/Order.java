package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    private Integer id;

    @SerializedName("date")
    private Long date;

    @SerializedName("service")
    private Service service;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
