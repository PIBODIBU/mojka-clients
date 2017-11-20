package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeocoderWrapper {
    @SerializedName("results")
    private List<GeocoderResult> geocoderResults;

    public List<GeocoderResult> getGeocoderResults() {
        return geocoderResults;
    }

    public void setGeocoderResults(List<GeocoderResult> geocoderResults) {
        this.geocoderResults = geocoderResults;
    }
}
