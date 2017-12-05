package com.mojka.poisk.data.api.inrerfaces;

import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MiscAPI {
    @GET("cities")
    Call<BaseDataWrapper<List<City>>> getCities();
}
