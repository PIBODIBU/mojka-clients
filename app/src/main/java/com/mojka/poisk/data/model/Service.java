package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Service implements Serializable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    public Service(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class CarWash {
        public static Service get() {
            return new Service("Мойка авто");
        }
    }

    public static class WheelRepair {
        public static Service get() {
            return new Service("Шиномонтаж");
        }
    }
}
