package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeocoderResult {
    @SerializedName("address_components")
    private List<AddressComponent> addressComponents;

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public static class AddressComponent {
        @SerializedName("long_name")
        private String longName;

        @SerializedName("short_name")
        private String shortName;

        public String getLongName() {
            return longName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
    }
}
