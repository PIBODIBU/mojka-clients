package com.mojka.poisk.ui.contract;

import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface ForgotPasswordContract {
    interface View extends BaseView {
        void showSaveButton();

        void hideSaveButton();

        void showGetCodeButton();

        void hideGetCodeButton();

        void showEtPassword();

        void hideEtPassword();

        void setEtPhoneEditable(Boolean editable);

        void sendCode();

        void save();

        String getPassword();

        String getPhone();

        String getCode();
    }

    interface Presenter extends BasePresenter<View> {
        void save();

        void sendCode();

        void verifyCode();
    }
}