package com.mojka.poisk.ui.contract;

import android.support.annotation.StringRes;

import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.ui.adapter.CarListAdapter;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

import java.util.List;

public interface CarListContract {
    interface View extends BaseView {
        void showToast(@StringRes int stringId);

        void showProgressBar();

        void hideProgressBar();

        void addCar();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchCarList();

        CarListAdapter getAdapter();

        void setupAdapter(List<Car> cars);
    }
}
