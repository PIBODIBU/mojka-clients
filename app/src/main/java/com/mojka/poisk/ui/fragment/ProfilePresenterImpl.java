package com.mojka.poisk.ui.fragment;

import com.mojka.poisk.ui.contract.ProfileContract;

public class ProfilePresenterImpl implements ProfileContract.Presenter {
    private ProfileContract.View view;

    @Override
    public void start() {

    }

    @Override
    public void setView(ProfileContract.View view) {
        this.view = view;
    }
}
