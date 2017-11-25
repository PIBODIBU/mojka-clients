package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

public class BaseDataWrapper<T> extends BaseErrorResponse {
    @SerializedName("data")
    private T responseObj;

    public BaseDataWrapper(T responseObj) {
        this.responseObj = responseObj;
    }

    public T getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(T responseObj) {
        this.responseObj = responseObj;
    }
}
