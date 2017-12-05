package com.mojka.poisk.ui.contract;

import com.mojka.poisk.ui.adapter.SettingsCityAdapter;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface SettingsCityContract {
    interface View extends BaseView {
        void show();

        void hide();

        Boolean isShowing();

        View getMVPView();

        void setupRecyclerView();

        void showProgressBar();

        void hideProgressBar();

        void showToast(String text);

        void setCity(String city);

        void save();
    }

    interface Presenter extends BasePresenter<View> {
        SettingsCityAdapter getAdapter();

        void setupAdapter();

        void fetchCities();

        void save(String city);
    }
}
