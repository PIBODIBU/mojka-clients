package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    public City(String name) {
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
}
