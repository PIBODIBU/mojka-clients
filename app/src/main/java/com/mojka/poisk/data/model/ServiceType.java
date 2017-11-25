package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceType implements Serializable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    public ServiceType(String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceType that = (ServiceType) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public static class CarWash {
        public static ServiceType get() {
            return new ServiceType("Мойка авто");
        }
    }

    public static class WheelRepair {
        public static ServiceType get() {
            return new ServiceType("Шиномонтаж");
        }
    }
}
