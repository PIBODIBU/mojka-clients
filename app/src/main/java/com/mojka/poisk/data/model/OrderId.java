package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

public class OrderId {
    @SerializedName("order_id")
    private Integer orderId;

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
