package com.mojka.poisk.ui.contract;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StringRes;

import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.databinding.ActivityCarEditBinding;
import com.mojka.poisk.ui.adapter.CarEditAdapter;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface CarEditContract {
    interface View extends BaseView {
        ActivityCarEditBinding getViewBinding();

        void showToast(@StringRes int stringId);

        void showProgressBar();

        void hideProgressBar();

        void addCar();

        void addPhoto();
    }

    interface Presenter extends BasePresenter<View> {
        void updateCar();

        Car getCar();

        void onSelectImage(Uri uri);

        void setupAdapter(Car car);

        CarEditAdapter getAdapter();

        Boolean checkIntent(Intent intent);

        Integer fetchCarIdFromIntent(Intent intent);

        void fetchCar();

        void setCar(Car car);
    }
}
