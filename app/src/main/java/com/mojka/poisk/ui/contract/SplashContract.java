package com.mojka.poisk.ui.contract;

import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface SplashContract {
    interface View extends BaseView {
        void startNextActivity();
    }

    interface Presenter extends BasePresenter<View> {

    }
}