package com.mojka.poisk.ui.contract;

import android.content.Intent;
import android.support.annotation.StringRes;

import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface ServiceDetailsContract {
    interface View extends BaseView {
        Intent getIntentFromView();

        void createOrder();

        void showProgressBar();

        void hideProgressBar();

        void setErrorText(@StringRes int stringId);

        void setupUi(Service service);

        void showNextImage();

        void showPreviousImage();

        void reload();

        void showToast(@StringRes int stringId);
    }

    interface Presenter extends BasePresenter<View> {
        Boolean checkIntent(Intent intent);

        void fetchService();

        void createOrder(Long time);

        Integer getServiceIdFromIntent(Intent intent);

        void setService(Service service);

        Service getService();
    }
}
