package com.mojka.poisk.data.callback;

import retrofit2.Call;
import retrofit2.Response;

public abstract class Callback<T> implements retrofit2.Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onDone();

        if (response == null || !response.isSuccessful() || response.body() == null)
            onError();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onDone();
        onError();
    }

    public abstract void onError();

    public abstract void onDone();
}
