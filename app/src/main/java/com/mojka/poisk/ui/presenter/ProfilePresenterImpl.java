package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.ui.contract.ProfileContract;

public class ProfilePresenterImpl implements ProfileContract.Presenter {
    private ProfileContract.View view;

    @Override
    public void start() {
        view.setupUi();
    }

    @Override
    public void setView(ProfileContract.View view) {
        this.view = view;
    }
}
