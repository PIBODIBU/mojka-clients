package com.mojka.poisk.data.api.inrerfaces;

import com.mojka.poisk.data.model.GeocoderWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocoderAPI {
    @GET("json")
    Call<GeocoderWrapper> getFromLocation(@Query("latlng") String latLng,
                                          @Query("sensor") Boolean sensor,
                                          @Query("language") String lang,
                                          @Query("key") String key);

    @GET("json")
    Call<GeocoderWrapper> getLatLngFromCityName(@Query("address") String city,
                                                @Query("sensor") Boolean sensor,
                                                @Query("language") String lang,
                                                @Query("key") String key);
}
