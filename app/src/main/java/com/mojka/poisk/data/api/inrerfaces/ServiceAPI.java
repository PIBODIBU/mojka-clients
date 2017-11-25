package com.mojka.poisk.data.api.inrerfaces;

import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceAPI {
    @GET("service")
    Call<BaseDataWrapper<List<Service>>> getAllServices();

    @GET("service/wash")
    Call<BaseDataWrapper<List<Service>>> getWashServices();

    @GET("service/repair")
    Call<BaseDataWrapper<List<Service>>> getRepairServices();
}
