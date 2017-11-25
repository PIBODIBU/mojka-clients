package com.mojka.poisk.data.api.inrerfaces;


import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("account")
    Call<BaseDataWrapper<User>> getInfo(@Query("token") String token);
}