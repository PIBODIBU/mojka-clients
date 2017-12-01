package com.mojka.poisk.ui.contract;

import android.support.annotation.StringRes;

import com.mojka.poisk.data.model.Order;
import com.mojka.poisk.ui.adapter.OrderListActiveAdapter;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

import java.util.List;

public interface OrderListContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

    }

    interface Active {
        interface View extends BaseView {
            void showLoadingScreen();

            void hideLoadingScreen();

            void showToast(@StringRes int stringId);
        }

        interface Presenter extends BasePresenter<View> {
            void fetchOrders();

            OrderListActiveAdapter getAdapter();

            void setupAdapter(List<Order> orders);
        }
    }

    interface History {
        interface View extends BaseView {

        }

        interface Presenter extends BasePresenter<View> {

        }
    }
}
