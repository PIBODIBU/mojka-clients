package com.mojka.poisk.data.api.inrerfaces;

import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.Car;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface CarAPI {
    @GET("account/car")
    Call<BaseDataWrapper<List<Car>>> getCars(@Query("token") String token);

    @Multipart
    @POST("account/car")
    Call<BaseErrorResponse> addCar(@Part("token") String token,
                                   @Part("name") String name,
                                   @Part("car_numbers") String carNumbers,
                                   @Part List<MultipartBody.Part> images);
}
