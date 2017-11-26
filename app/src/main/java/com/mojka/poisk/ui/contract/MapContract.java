package com.mojka.poisk.ui.contract;

import android.support.annotation.StringRes;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

import java.util.List;

public interface MapContract {
    interface View extends BaseView {
        void setupMap();

        void setBottomBarTitle(@StringRes int title);

        void setServiceName(String name);

        void showBottomBar();

        void hideBottomBar();

        void chooseAnotherFilters();

        void toggleFilterWindow();

        void setupFilterWindow();

        MapFilterWindowContract.View getFilterMVP();

        void checkPermission();

        void onPermissionsGranted();
    }

    interface Presenter extends BasePresenter<View> {
        OnMapReadyCallback getOnMapReadyCallback();

        void fetchFilter();

        Boolean hasFilter();

        Boolean hasManyServicesInFilter();

        void setupBottomBar();

        void setupUserLocationUpdates();

        void setupGoogleApi();

        void fetchServices(Callback<BaseDataWrapper<List<Service>>> callback);

        Service getSelectedService();

        void setSelectedService(Service service);

        void setupFilterWindow();
    }
}
