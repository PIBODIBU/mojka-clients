package com.mojka.poisk.data.api.inrerfaces;

import com.mojka.poisk.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginAPI {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<LoginResponse> register(@Field("phone") String phone,
                                 @Field("password") String password,
                                 @Field("name") String name,
                                 @Field("city") String city,
                                 @Field("car") String car);
}
