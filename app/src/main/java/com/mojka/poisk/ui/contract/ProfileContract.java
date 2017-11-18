package com.mojka.poisk.ui.contract;

import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface ProfileContract {
    interface View extends BaseView {
        void chooseService();

        void toggleSettingsWindow();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
