package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Car {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("car_numbers")
    private String numbers;

    @SerializedName("images")
    private List<Image> images;

    public Car() {
    }

    public Car(Integer id, String name, String numbers, List<Image> images) {
        this.id = id;
        this.name = name;
        this.numbers = numbers;
        this.images = images;
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

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
