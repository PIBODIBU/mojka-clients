package com.mojka.poisk.data.api.inrerfaces;

import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderAPI {
    @GET("service/order")
    Call<BaseDataWrapper<List<Order>>> getOrders(@Query("token") String token);

    @FormUrlEncoded
    @POST("service/addorder")
    Call<BaseErrorResponse> createOrder(@Field("service_id") Integer serviceId,
                                        @Field("date") Long date,
                                        @Field("token") String token);

    @FormUrlEncoded
    @POST("service/moveorder")
    Call<BaseErrorResponse> moveOrder(@Field("order_id") Integer orderId,
                                      @Field("new_date") Long newDate,
                                      @Field("token") String token);

    @FormUrlEncoded
    @POST("service/cancelorder")
    Call<BaseErrorResponse> cancelOrder(@Field("order_id") Integer orderId,
                                        @Field("token") String token);
}
