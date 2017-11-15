package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.ui.contract.RegisterContract;

public class RegisterPresenterImpl implements RegisterContract.Presenter {
    private final String TAG = "RegisterPresenterImpl";

    private RegisterContract.View view;

    @Override
    public void start() {
    }

    @Override
    public void setView(RegisterContract.View view) {
        this.view = view;
    }
}
