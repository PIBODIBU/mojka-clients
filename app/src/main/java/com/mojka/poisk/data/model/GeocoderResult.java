package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeocoderResult {
    @SerializedName("address_components")
    private List<AddressComponent> addressComponents;

    private GeocoderGeometry geometry;

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public GeocoderGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GeocoderGeometry geometry) {
        this.geometry = geometry;
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

    public static class GeocoderGeometry {
        private GeocoderLocation location;

        public GeocoderLocation getLocation() {
            return location;
        }

        public void setLocation(GeocoderLocation location) {
            this.location = location;
        }

        public static class GeocoderLocation {
            private String lat;
            private String lng;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }
        }

    }
}
