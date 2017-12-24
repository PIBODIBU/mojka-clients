package com.mojka.poisk.data.api.inrerfaces;

import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.FeedbackSubject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FeedbackAPI {
    @GET("feedback/subject")
    Call<BaseDataWrapper<List<FeedbackSubject>>> getSubjects();

    @FormUrlEncoded
    @POST("feedback")
    Call<BaseErrorResponse> send(@Field("token") String token,
                                 @Field("subject") String subject,
                                 @Field("email") String email,
                                 @Field("message") String message);
}
