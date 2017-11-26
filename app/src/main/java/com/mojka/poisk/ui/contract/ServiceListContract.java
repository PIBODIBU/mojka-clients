package com.mojka.poisk.ui.contract;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;

import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface ServiceListContract {
    interface View extends BaseView {
        void showToast(@StringRes int stringId);

        void showProgressBar();

        void hideProgressBar();

        void setRefreshing(Boolean refreshing);
    }

    interface Presenter extends BasePresenter<View> {
        void fetchServices();

        void setRecyclerView(RecyclerView recyclerView);

        void setupRecyclerView();
    }
}
