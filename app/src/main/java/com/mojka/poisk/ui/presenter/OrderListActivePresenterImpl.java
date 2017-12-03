package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.OrderAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.Order;
import com.mojka.poisk.ui.adapter.OrderListActiveAdapter;
import com.mojka.poisk.ui.contract.OrderListContract;

import java.util.LinkedList;
import java.util.List;

public class OrderListActivePresenterImpl implements OrderListContract.Active.Presenter {
    private OrderListContract.Active.View view;
    private OrderListActiveAdapter adapter;

    @Override
    public void start() {
        fetchOrders();
    }

    @Override
    public void setView(OrderListContract.Active.View view) {
        this.view = view;
    }

    @Override
    public OrderListActiveAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setupAdapter(List<Order> orders) {
        adapter = new OrderListActiveAdapter(view.getViewContext(), orders);

        if (orders.size() == 0)
            view.showEmptyListAlert();
        else
            view.hideEmptyListAlert();
    }

    @Override
    public void fetchOrders() {
        view.showLoadingScreen();

        APIGenerator.createService(OrderAPI.class).getOrders(new AccountService(view.getViewContext()).getToken())
                .enqueue(new Callback<BaseDataWrapper<List<Order>>>() {
                    @Override
                    public void onSuccess(BaseDataWrapper<List<Order>> response) {
                        if (response.getError()) {
                            onError();
                            return;
                        }

                        List<Order> orders = new LinkedList<>();

                        for (Order order : response.getResponseObj())
                            if (!order.getDone())
                                orders.add(order);

                        setupAdapter(orders);
                        view.setupUi();
                    }

                    @Override
                    public void onError() {
                        view.showToast(R.string.toast_order_active_fetch_error);
                    }

                    @Override
                    public void onDone() {
                        view.hideLoadingScreen();
                    }
                });
    }
}
