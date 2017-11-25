package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Service {
    public static final Integer TYPE_WASH = 1;
    public static final Integer TYPE_REPAIR = 2;

    @SerializedName("id")
    private Integer id;

    @SerializedName("type")
    private Integer type;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("working_hours")
    private String workingHours;

    @SerializedName("images")
    private List<Image> images;

    @SerializedName("price_start")
    private Integer priceStart;

    @SerializedName("price_end")
    private Integer priceEnd;

    @SerializedName("nearest_entry")
    private Long nearestEntry;

    @SerializedName("lat")
    private Double lat;

    @SerializedName("lng")
    private Double lng;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Integer getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(Integer priceStart) {
        this.priceStart = priceStart;
    }

    public Integer getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Integer priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Long getNearestEntry() {
        return nearestEntry;
    }

    public void setNearestEntry(Long nearestEntry) {
        this.nearestEntry = nearestEntry;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
