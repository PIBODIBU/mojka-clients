package com.mojka.poisk.ui.contract;

import android.net.Uri;
import android.support.annotation.StringRes;

import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.ui.adapter.CarAddAdapter;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface CarAddContract {
    interface View extends BaseView {
        void showToast(@StringRes int stringId);

        void showProgressBar();

        void hideProgressBar();

        void addCar();

        void addPhoto();

        void setupUi(Car car);
    }

    interface Presenter extends BasePresenter<View> {
        void addCar();

        Car getCar();

        void onSelectImage(Uri uri);

        void setupAdapter();

        CarAddAdapter getAdapter();
    }
}
