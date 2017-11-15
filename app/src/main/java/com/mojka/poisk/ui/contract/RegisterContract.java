package com.mojka.poisk.ui.contract;

import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public class RegisterContract {
    public interface View extends BaseView {
        void next();
    }

    public interface Presenter extends BasePresenter<View> {
        void verifyPhoneNumber(String phoneNumber);

        String getPhoneNumber();
    }
}
