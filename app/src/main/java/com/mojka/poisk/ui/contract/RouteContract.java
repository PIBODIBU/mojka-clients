package com.mojka.poisk.ui.contract;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface RouteContract {
    interface View extends BaseView {
        void setupMap();

        void onPermissionsGranted();

        void checkPermission();


    }

    interface Presenter extends BasePresenter<View> {
        Boolean checkIntent();

        OnMapReadyCallback getOnMapReadyCallBack();

        void setupGoogleApi();

        void getUserLocation();

        void createRoute();
    }
}
