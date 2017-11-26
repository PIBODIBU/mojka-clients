package com.mojka.poisk.data.callback;

import retrofit2.Call;
import retrofit2.Response;

public abstract class Callback<T> implements retrofit2.Callback<T> {
    private static final String TAG = "Callback<T>";

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response == null || !response.isSuccessful() || response.body() == null) {
            onError();
            onDone();
            return;
        }

        onSuccess(response.body());
        onDone();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onError();
        onDone();
    }


    public void onSuccess(T response) {
    }

    public void onError() {
    }

    public void onDone() {
    }
}
