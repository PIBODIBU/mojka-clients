package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.ui.contract.LoginContract;

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View view;

    @Override
    public void start() {

    }

    @Override
    public void setView(LoginContract.View view) {
        this.view = view;
    }
}
