package com.mojka.poisk.data.api.inrerfaces;


import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("account")
    Call<BaseDataWrapper<User>> getInfo(@Query("token") String token);

    @FormUrlEncoded
    @POST("updateaccount")
    Call<BaseErrorResponse> updateCity(@Field("token") String token,
                                       @Field("city") String city);
}