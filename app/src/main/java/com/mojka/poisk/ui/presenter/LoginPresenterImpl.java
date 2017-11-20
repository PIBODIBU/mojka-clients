package com.mojka.poisk.ui.presenter;

import android.text.TextUtils;

import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.LoginAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.LoginResponse;
import com.mojka.poisk.data.model.User;
import com.mojka.poisk.ui.contract.LoginContract;

import retrofit2.Call;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View view;

    @Override
    public void start() {
        view.setupUi();
    }

    @Override
    public void login() {
        if (TextUtils.isEmpty(view.getPhoneNumber()) || TextUtils.isEmpty(view.getPassword())) {
            view.showToast(R.string.enter_phone_and_password);
            return;
        }

        view.showProgressBar();
        view.freezeUI();

        APIGenerator.createService(LoginAPI.class).login(view.getPhoneNumber(), view.getPassword()).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onResponse(call, response);

                if (response.body().getToken() == null) {
                    onDone();
                    onError();
                    return;
                }

//                new AccountService(view.getViewContext()).setAccount());
            }
            
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                super.onFailure(call, t);
            }

            @Override
            public void onError() {
                view.showToast(R.string.error_login);
            }

            @Override
            public void onDone() {
                view.unfreezeUI();
                view.hideProgressBar();
            }
        });
    }

    @Override
    public void setView(LoginContract.View view) {
        this.view = view;
    }
}
