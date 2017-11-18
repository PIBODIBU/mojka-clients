package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

public abstract class BaseErrorResponse {
    @SerializedName("error")
    private Boolean error;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
