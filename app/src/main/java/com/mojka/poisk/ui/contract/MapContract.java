package com.mojka.poisk.ui.contract;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface MapContract {
    interface View extends BaseView {
        void setupMap();
    }

    interface Presenter extends BasePresenter<View> {
        OnMapReadyCallback getOnMapReadyCallback();
    }
}
