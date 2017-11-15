package com.mojka.poisk.ui.contract;

import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface LoginContract {
    interface View extends BaseView {
        void loadBackground();

        void register();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
